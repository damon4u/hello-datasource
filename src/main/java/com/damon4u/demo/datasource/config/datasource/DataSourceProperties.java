package com.damon4u.demo.datasource.config.datasource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description:
 *
 * 读取参数配置
 * 使用的时候，使用EnableConfigurationProperties注册为一个bean
 *
 * @author damon4u
 * @version 2017-09-21 11:46
 */
@ConfigurationProperties(prefix = "app.datasource")
@Setter
@Getter
public class DataSourceProperties {

    private String readDataSourceSize;

}
