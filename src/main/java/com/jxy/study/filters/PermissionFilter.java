package com.jxy.study.filters;

import com.alibaba.druid.support.json.JSONUtils;
import com.jxy.study.dao.RoleDao;
import com.jxy.study.dao.RolePermissionDao;
import com.jxy.study.entity.Role;
import com.jxy.study.entity.User;
import com.jxy.study.service.UserService;
import com.jxy.study.util.SpringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

/**
 * @description 自定义权限过滤器
 * @author: jxy
 * @create: 2019-09-16 16:14
 */
public class PermissionFilter extends AccessControlFilter {

    private UserService userService = SpringUtil.getBean(UserService.class);

    private RoleDao roleDao = SpringUtil.getBean(RoleDao.class);

    private RolePermissionDao rolePermissionDao = SpringUtil.getBean(RolePermissionDao.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.getByName(userName);
        Role role = roleDao.getRoleById(user.getRoleId());
        List<String> permissionUrls = rolePermissionDao.getPermissionUrls(role.getId());
        if (permissionUrls.contains(request.getRequestURI())) {
            return true;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.UNAUTHORIZED.value());
        map.put("msg", "无权限");
        response.setContentType("application/json; charset=utf-8");
        //返回json
        response.getWriter().write(JSONUtils.toJSONString(map));
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
}
