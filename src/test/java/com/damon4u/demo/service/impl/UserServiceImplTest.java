package com.damon4u.demo.service.impl;

import com.damon4u.demo.domain.User;
import com.damon4u.demo.exception.ServiceException;
import com.damon4u.demo.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

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
    }

    @Test
    public void getUserByName() throws Exception {
        List<User> userByName = userService.getUserByName("test");
        logger.info("userName={}", userByName);
    }

    @Test(expected = ServiceException.class)
    public void saveThrowExceptionWithTransaction() throws Exception {
        User user = new User();
        user.setUserName("tom2");
        userService.saveThrowExceptionWithTransaction(user);
    }

    @Test(expected = RuntimeException.class)
    public void saveThrowExceptionWithoutTransaction() throws Exception {
        User user = new User();
        user.setUserName("tom");
        userService.saveThrowExceptionWithoutTransaction(user);
    }

    @Test
    public void readThenWrite() {
        User user = new User();
        user.setUserName("tom3");
        userService.readThenWrite(user);
    }

    @Test
    public void readThenWriteGetBean() {
        User user = new User();
        user.setUserName("tom3");
        userService.readThenWriteGetBean(user);
    }

}