package com.jxy.study.dao;


import com.jxy.study.entity.Permission;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PermissionDao {

  List<Permission> getByMap(Map<String, Object> map);

  Permission getById(Integer id);

  List<Permission> getByIds(List<Integer> ids);

  Integer create(Permission permission);

  int update(Permission permission);

  int delete(Integer id);

  List<Permission> getList();

  List<Permission> getByUserId(List<Integer> userId);

}