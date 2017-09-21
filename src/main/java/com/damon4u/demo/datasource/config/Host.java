package com.damon4u.demo.datasource.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description:
 *
 * @author damon4u
 * @version 2017-09-20 22:30
 */
@Data
public class Host {

    private String ip;

    private int port;
}
