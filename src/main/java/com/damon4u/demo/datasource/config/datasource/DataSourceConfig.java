package com.damon4u.demo.datasource.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Description:
 *
 * 数据源配置
 *
 * 注意，Bean注解指定name属性，使用时用Resource注解
 *
 * @author damon4u
 * @version 2017-09-20 22:37
 */
@Configuration
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    /************* master *************/
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.write")
    public DataSourceProperties writeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "writeDataSource")
    @Primary
    @ConfigurationProperties("app.datasource.write")
    public DataSource writeDataSource() {
        logger.info("-------------------- writeDataSource init ---------------------");
        return writeDataSourceProperties().initializeDataSourceBuilder().build();
    }

    /************* read01 *************/
    @Bean
    @ConfigurationProperties("app.datasource.read01")
    public DataSourceProperties readDataSourceOneProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "readDataSource01")
    @ConfigurationProperties("app.datasource.read01")
    public DataSource readDataSourceOne() {
        logger.info("-------------------- read01 DataSourceOne init ---------------------");
        return readDataSourceOneProperties().initializeDataSourceBuilder().build();
    }

    /************* read02 *************/
    @Bean
    @ConfigurationProperties("app.datasource.read02")
    public DataSourceProperties readDataSourceTwoProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "readDataSource02")
    @ConfigurationProperties("app.datasource.read02")
    public DataSource readDataSourceTow() {
        logger.info("-------------------- read02 DataSourceTwo init ---------------------");
        return readDataSourceTwoProperties().initializeDataSourceBuilder().build();
    }
}
