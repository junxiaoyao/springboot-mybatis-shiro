package com.jxy.study.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description
 * @author: jxy
 * @create: 2019-08-29 16:50
 */
public class CookieUtil {
    private final static Logger log= LoggerFactory.getLogger(Cookie.class);
    public final static String COOKIE_NAME="JSESSIONID";
    public final static String COOKIE_DOMAIN="";
    //1. 把登录信息存入cookie 中
//CookieUtil.writeLoginToken(httpServletResponse,session.getId());
//-----------------》writeLoginToken()方法
    /**
     * 存入cookie到本地
     */
    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie ck = new Cookie(COOKIE_NAME,token);
        //跨域共享，设置的值是B机器的域名，所以当B机器访问这个Cookie的时候是能访问到的
//        ck.setDomain(COOKIE_DOMAIN);
        //代表设置在根目录
        ck.setPath("/");
        ck.setHttpOnly(true);
        //单位是秒。如果这个maxAge不设置的话，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        //如果是-1，代表永久
        ck.setMaxAge(60 * 60 * 24 * 365);
        log.info("write cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
        response.addCookie(ck);
    }
}
