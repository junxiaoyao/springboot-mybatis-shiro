package com.jxy.study.service;

import com.jxy.study.dao.UserDao;
import com.jxy.study.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description
 * @author: jxy
 * @create: 2019-06-11 11:24
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getByName(String name) {
        return userDao.getByName(name);
    }

    public void addUser(User u) {
        User user = new User();
        user.setName("1");
        user.setPassword("1");
        user.setRoleId(1);
        userDao.addUser(user);
        int i = 1 / 0;
        User user2 = new User();
        user2.setName("2");
        user2.setPassword("2");
        user2.setRoleId(2);
        userDao.addUser(user);
      //  userDao.addUser(u);
    }
}
