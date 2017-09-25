package com.damon4u.demo.service.impl;

import com.damon4u.demo.annotation.ReadDataSource;
import com.damon4u.demo.annotation.WriteDataSource;
import com.damon4u.demo.dao.UserMapper;
import com.damon4u.demo.domain.User;
import com.damon4u.demo.exception.ServiceException;
import com.damon4u.demo.service.UserService;
import com.damon4u.demo.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * 如果需要事务，自行在方法上添加@Transactional
 * 如果方法有内部有数据库操作，则必须指定@WriteDataSource还是@ReadDataSource
 *
 * 注：AOP ，内部方法之间互相调用时，如果是this.xxx()这形式，不会触发AOP拦截，可能会
 * 导致无法决定数据库是走写库还是读库
 * 方法：
 * 为了触发AOP的拦截，调用内部方法时，需要特殊处理下，看方法getService()
 *
 *
 * @author damon4u
 * @version 2017-09-21 13:22
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    @WriteDataSource
    public void save(User user) {
        userMapper.insert(user);
    }

    @Override
    @ReadDataSource
    public User getUser(Long id) {
        return userMapper.findById(id);
    }

    @Override
    @ReadDataSource
    public List<User> getUserByName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    @WriteDataSource
    @Transactional(rollbackFor = ServiceException.class)
    public void saveThrowExceptionWithTransaction(User user) throws ServiceException {
        userMapper.insert(user);
        throw new ServiceException("db exception");
    }

    @Override
    @WriteDataSource
    public void saveThrowExceptionWithoutTransaction(User user) throws ServiceException {
        userMapper.insert(user);
        throw new ServiceException("db exception");
    }

    @Override
    public void readThenWrite(User user) {
        User user1 = getUser(1L);
        logger.info("user={}", user1);
        save(user);
    }

    @Override
    public void readThenWriteGetBean(User user) {
        UserService service = getService();
        User user1 = service.getUser(1L);
        logger.info("user={}", user1);
        service.save(user);
    }

    public UserService getService() {
        return SpringContextUtil.getBean(getClass());
    }
}
