package com.jxy.study.filters;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.support.json.JSONUtils;
import com.jxy.study.util.SomeStaticString;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

/**
 * @description 登录过滤器
 * @author: jxy
 * @create: 2019-09-19 16:28
 */
public class LoginFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o)
            throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 如果是登录页面进入放过
        if (request.getMethod().equals(HttpMethod.GET.name())) {
            return true;
        }
        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        Map<String, String[]> paramMaps = request.getParameterMap();
        String userName = String.valueOf(paramMaps.get("name")[0]);
        String password = String.valueOf(paramMaps.get("password")[0]);
        //  Boolean rememberMe = new Boolean(String.valueOf(paramMaps.get("rememberMe")[0]));
        boolean rememberMe = true;
        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password, rememberMe);
        Map<String, Object> object = new HashMap<>();
        // 3.执行登录方法
        try {
            subject.login(token);
            String url = "/mainPage";
            // 获取未登录之前地址
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            // 如果未登录之前有地址，跳转至原地址
            if (savedRequest != null) {
                url = savedRequest.getRequestUrl();
            }
            // 登录成功,进入主页
            object.put("url", url);
            object.put("code", 200);
        } catch (UnknownAccountException e) {
            object.put("code", HttpStatus.BAD_REQUEST.value());
        } catch (IncorrectCredentialsException e) {
            // 登录失败:密码错误
            object.put("code", HttpStatus.UNAUTHORIZED.value());
        }
        response.setContentType("application/json; charset=utf-8");//返回json
        response.getWriter().write(JSONUtils.toJSONString(object));
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
