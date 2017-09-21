package com.damon4u.demo.service;

import com.damon4u.demo.domain.User;

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

}
