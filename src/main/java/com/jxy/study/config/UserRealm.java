package com.jxy.study.config;

import com.jxy.study.dao.PermissionDao;
import com.jxy.study.dao.RoleDao;
import com.jxy.study.dao.RolePermissionDao;
import com.jxy.study.entity.Permission;
import com.jxy.study.entity.Role;
import com.jxy.study.entity.RolePermission;
import com.jxy.study.entity.User;
import com.jxy.study.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description
 * @author: jxy
 * @create: 2019-06-11 09:46
 */
public class UserRealm extends AuthorizingRealm {

  @Autowired
  private UserService userService;

  @Autowired
  private RoleDao roleDao;

  @Autowired
  private RolePermissionDao rolePermissionDao;

  @Autowired
  private PermissionDao permissionDao;

  //授权
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    System.out.println("执行授权逻辑");
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    String userName = (String) SecurityUtils.getSubject().getPrincipal();
    User user = userService.getByName(userName);
    Role role = roleDao.getRoleById(user.getRoleId());
    Set<String> roles = new HashSet<>();
    roles.add(role.getName());
    List<Integer> permissonIds = rolePermissionDao.getPermissionIds(role.getId());
    //给资源进行授权

    List<Permission> permissions = permissionDao.getByUserId(permissonIds);
    for (Permission permission : permissions) {
     // info.addStringPermission(permission.getPermissionUrl());
    }
    //设置角色
    info.setRoles(roles);
    return info;
  }

  //认证
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    // System.out.println("执行认证逻辑");
    //编写shiro判断逻辑，判断用户名和密码
    //1.判断用户名
    UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
    User user = userService.getByName(token.getUsername());
    if (user == null) {
      //用户名不存在
      return null;//shiro底层会抛出UnKnowAccountException
    }
    //2.认证密码
    return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), "");

  }
}
