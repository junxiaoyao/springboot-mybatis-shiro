package com.jxy.study.dao;

import com.jxy.study.entity.User;
import java.util.List;
import java.util.Map;

public interface UserDao {

	List<User> getByMap(Map<String, Object> map);
	User getById(Integer id);
	Integer create(User user);
	int update(User user);
	int delete(Integer id);
	User getByUserName(String userName);
}