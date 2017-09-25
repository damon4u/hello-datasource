package com.damon4u.demo.service;

import com.damon4u.demo.domain.User;
import com.damon4u.demo.exception.ServiceException;

import java.util.List;

/**
 * Description:
 *
 * @author damon4u
 * @version 2017-08-22 13:21
 */
public interface UserService {

    void save(User user);

    User getUser(Long id);

    List<User> getUserByName(String userName);

    /**
     * 保存过程中抛出异常
     * 事务中，数据回滚
     * 注意，Transactional注解默认对RuntimeException和Error异常回滚，
     * 业务自定义的业务异常并不会回滚，需要制定callbackFor属性
     */
    void saveThrowExceptionWithTransaction(User user) throws ServiceException;

    /**
     * 保存过程中抛出异常
     * 非事务，数据不回滚
     */
    void saveThrowExceptionWithoutTransaction(User user) throws ServiceException;

    /**
     * 方法内先调用读方法，再调用写方法
     * 由于方法本身没有显示使用注解指定数据源
     * 那么默认使用写库
     * 又aop后原来的service换成了代理类，调用代理类内部方法时不会被aop拦截，
     * 不会切换数据源，调用读方法时也不会切换到读库
     * @param user
     */
    void readThenWrite(User user);

    /**
     * 调用内部方法时，如果想要切换数据源，可以从context中获取bean
     *
     * @param user
     */
    void readThenWriteGetBean(User user);


}
