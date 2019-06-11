package com.jxy.study.dao;

import com.jxy.study.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface RoleDao {

  Role getRoleById(Integer id);
}
