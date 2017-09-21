package com.damon4u.demo.datasource.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author damon4u
 * @version 2017-09-20 22:03
 */
@Component
@ConfigurationProperties("live")
@Setter
@Getter
@ToString
public class CommonConfig {

    /**
     * 注意，内部定义嵌套类型必须声明为public static
     */
    @Data
    public static class LiveUrl {

        private String preview;

        private String start;

        private String stop;
    }

    private LiveUrl url; // 内部定义的类型，不需要特别指定嵌套

    @NestedConfigurationProperty
    private Host host; // 外部定义的类型，需要使用注解指定嵌套属性

    private List<String> whitelist;

    private Map<String, Integer> timeout;
}
