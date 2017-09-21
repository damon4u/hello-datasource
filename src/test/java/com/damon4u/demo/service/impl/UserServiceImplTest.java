package com.damon4u.demo.service.impl;

import com.damon4u.demo.domain.User;
import com.damon4u.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Description:
 *
 * @author damon4u
 * @version 2017-09-21 19:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.profiles.active=dev"})
public class UserServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

    @Resource
    private UserService userService;

    @Test
    public void save() throws Exception {
        User user = new User();
        user.setUserName("tom");
        userService.save(user);
    }

    @Test
    public void getUser() throws Exception {
        User user = userService.getUser(1L);
        logger.info("user={}", user);
        User user1 = userService.getUser(1L);
        logger.info("user1={}", user1);
    }

    @Test
    public void getUserByName() throws Exception {
    }

}