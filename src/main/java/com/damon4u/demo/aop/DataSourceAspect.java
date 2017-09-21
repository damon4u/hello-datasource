package com.damon4u.demo.aop;

import com.damon4u.demo.datasource.config.datasource.DataSourceContextHolder;
import com.damon4u.demo.datasource.config.datasource.DataSourceType;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Description:
 * service层的拦截，数据源切换
 * 不在dao层做，实现事务
 * 需要注意，aop的order要比事务的小，保证aop先执行，将数据源设置到context中
 *
 * @author damon4u
 * @version 2017-08-22 13:05
 */
@Aspect
@Order(1)
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@Component
public class DataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

    @Before("execution(* com.damon4u.demo.service..*.*(..)) && @annotation(com.damon4u.demo.annotation.ReadDataSource)")
    public void setReadDataSourceType() {
        //如果已经开启写事务了，那之后的所有读都从写库读
        if (!DataSourceType.WRITE.getType().equals(DataSourceContextHolder.getReadOrWrite())) {
            logger.info(">>>>>>>>>>>> setReadDataSourceType");
            DataSourceContextHolder.setRead();
        } else {
            logger.info(">>>>>>>>>>>> not setReadDataSourceType to write context");
        }
    }

    @Before("execution(* com.damon4u.demo.service..*.*(..)) && @annotation(com.damon4u.demo.annotation.WriteDataSource)")
    public void setWriteDataSourceType() {
        DataSourceContextHolder.setWrite();
        logger.info(">>>>>>>>>>>> setWriteDataSourceType");
    }

}
