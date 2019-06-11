package com.jxy.study.service;

import com.jxy.study.dao.UserDao;
import com.jxy.study.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description
 * @author: jxy
 * @create: 2019-06-11 11:24
 */
@Service
public class UserService {

  @Autowired
  private UserDao userDao;

  public User getByName(String name) {
    return userDao.getByName(name);
  }
}
