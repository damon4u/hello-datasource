package com.damon4u.demo.datasource.config.datasource;

import lombok.Getter;

/**
 * Description:
 *
 * @author damon4u
 * @version 2017-09-21 10:41
 */
@Getter
public enum DataSourceType {
    READ("read", "从库"),
    WRITE("write", "主库");

    private String type;

    private String name;

    DataSourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }

}
