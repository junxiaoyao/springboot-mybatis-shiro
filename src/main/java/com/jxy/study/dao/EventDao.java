package com.jxy.study.dao;

import com.jxy.study.entity.Event;
import java.util.List;
import java.util.Map;

public interface EventDao {

	List<Event> getByMap(Map<String, Object> map);
	Event getById(Integer id);
	Integer create(Event event);
	int update(Event event);
	int delete(Integer id);
}