//package com.jxy.study.filters;
//
//import com.alibaba.fastjson.JSON;
//import com.jxy.study.entity.User;
//import com.jxy.study.service.UserService;
//import com.jxy.study.util.SpringUtil;
//import com.jxy.study.util.jwt.JWTResult;
//import com.jxy.study.util.jwt.JWTSubject;
//import com.jxy.study.util.jwt.JWTUtil;
//import io.jsonwebtoken.Claims;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.AccessControlFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @Auther: jxy
// * @Date: 2019/9/26 19:49
// * @Description:
// */
//public class JwtFilter extends AccessControlFilter {
//    @Autowired
//    private UserService userService;
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String token = request.getHeader("authorization");
//        Claims claims = JWTUtil.parseJwt(token);
//        JWTSubject jwtSubject = JSON.parseObject(claims.getSubject(), JWTSubject.class);
//        User user = userService.getByName(jwtSubject.getUserName());
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getName(), user.getPassword());
//        try {
//            subject.login(usernamePasswordToken);
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
//        return false;
//    }
//}
