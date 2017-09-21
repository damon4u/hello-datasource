package com.damon4u.demo.datasource.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

import static org.junit.Assert.*;

/**
 * Description:
 *
 * @author damon4u
 * @version 2017-09-20 21:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonConfigTest {

    @Resource
    private CommonConfig commonConfig;

    @Resource
    private DataSource writeDataSource;

    @Resource(name = "readDataSource01")
    private DataSource readDataSource01;

    @Test
    public void test() {
        System.out.println(commonConfig);
        System.out.println(writeDataSource);
        System.out.println(readDataSource01);
    }

}