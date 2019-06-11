package com.jxy.study.dao;

import com.jxy.study.entity.RolePermission;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RolePermissionDao {

  List<RolePermission> getByRoleId(Integer roleId);

  List<Integer> getPermissionIds(Integer roleId);
}
