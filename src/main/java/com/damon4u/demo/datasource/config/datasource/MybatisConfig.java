package com.damon4u.demo.datasource.config.datasource;

import com.damon4u.demo.util.SpringContextUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 *
 * @author damon4u
 * @version 2017-09-21 10:44
 */
@Configuration
@AutoConfigureAfter(DataSourceConfig.class)
@MapperScan(basePackages = {"com.damon4u.demo.dao"})
@EnableTransactionManagement(order = 10)
@EnableConfigurationProperties(value = {MybatisProperties.class, DataSourceProperties.class})
public class MybatisConfig {
    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Resource
    private MybatisProperties properties;

    @Resource
    private DataSource writeDataSource;

    @Resource
    private DataSource readDataSource01;

    @Resource
    private DataSource readDataSource02;

    @Bean
    public SqlSessionFactory sqlSessionFactory() {
        logger.info("-------------------- sqlSessionFactory init ---------------------");
        try {
            SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
            factory.setDataSource(roundRobinDataSourceProxy());
            factory.setVfs(SpringBootVFS.class);
            if (org.springframework.util.StringUtils.hasText(this.properties.getConfigLocation())) {
                factory.setConfigLocation(new DefaultResourceLoader().getResource(this.properties.getConfigLocation()));
            }
            if (org.springframework.util.StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
                factory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
            }
            if (org.springframework.util.StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
                factory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
            }
            if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
                factory.setMapperLocations(this.properties.resolveMapperLocations());
            }
            return factory.getObject();
        } catch (Exception e) {
            logger.error("mybatis sqlSessionFactoryBean create error",e);
            return null;
        }
    }

    /**
     * 把所有数据库都放在路由中
     */
    @Bean(name="roundRobinDataSourceProxy")
    public AbstractRoutingDataSource roundRobinDataSourceProxy() {
        Map<Object, Object> targetDataSources = Maps.newHashMap();
        //把所有数据库都放在targetDataSources中,注意key值要和determineCurrentLookupKey()中代码写的一至，
        //否则切换数据源时找不到正确的数据源
        targetDataSources.put(DataSourceType.WRITE.getType(), writeDataSource);
        targetDataSources.put(DataSourceType.READ.getType() + "1", readDataSource01);
        targetDataSources.put(DataSourceType.READ.getType() + "2", readDataSource02);

        final int readSize = Integer.parseInt(dataSourceProperties.getReadDataSourceSize());

        //路由类，寻找对应的数据源
        AbstractRoutingDataSource proxy = new AbstractRoutingDataSource() {

            private AtomicInteger count = new AtomicInteger(0);
            /**
             * 这是AbstractRoutingDataSource类中的一个抽象方法，
             * 而它的返回值是你所要用的数据源dataSource的key值，有了这个key值，
             * targetDataSources就从中取出对应的DataSource，如果找不到，就用配置默认的数据源。
             */
            @Override
            protected Object determineCurrentLookupKey() {
                String typeKey = DataSourceContextHolder.getReadOrWrite();
                if (StringUtils.isBlank(typeKey)) {
                    logger.error(">>>>>>>>>>>> default use write database");
                    return DataSourceType.WRITE.getType();
//                    throw new NullPointerException("数据库路由时，决定使用哪个数据库源类型不能为空...");
                }
                if (typeKey.equals(DataSourceType.WRITE.getType())) {
                    logger.info(">>>>>>>>>>>> user datasource write.............");
                    return DataSourceType.WRITE.getType();
                }

                // 读库， 简单负载均衡
                int number = count.getAndAdd(1);
                int lookupKey = (number % readSize) + 1;
                logger.info(">>>>>>>>>>>> use datasource read0" + lookupKey);
                return DataSourceType.READ.getType() + lookupKey;
            }
        };

        proxy.setDefaultTargetDataSource(writeDataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    //事务管理
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        logger.info("-------------------- PlatformTransactionManager init ---------------------");
        return new DataSourceTransactionManager((DataSource) SpringContextUtil.getBean("roundRobinDataSourceProxy"));
    }


}
