package com.jxy.study.entity;

import lombok.Data;

/**
 * @description
 * @author: jxy
 * @create: 2019-06-11 14:42
 */
@Data
public class RolePermission {

  private Integer id;
  private Integer roleId;
  private Integer permissionId;
}
