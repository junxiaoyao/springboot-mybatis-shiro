package com.jxy.study.dao;

import com.jxy.study.entity.Hrole;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author: jxy
 * @create: 2019-06-10 15:32
 */
public interface HroleDao {

  List<Hrole> getByMap(Map<String, Object> map);
  Hrole getById(Integer id);
  Integer create(Hrole role);
  int update(Hrole role);
  int delete(Integer id);
}
