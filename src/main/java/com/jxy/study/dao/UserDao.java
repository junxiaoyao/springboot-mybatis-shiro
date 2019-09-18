package com.jxy.study.dao;

import com.jxy.study.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {

    User getById(Integer id);

    User getByName(String name);

    void addUser(User u);
}