package com.jxy.study.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.stereotype.Component;

/**
 * @description
 * @author: jxy
 * @create: 2019-07-26 08:59
 */
@Component
public class ShiroFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o)
        throws Exception {
        System.out.println("测试自定义拦截");
        return true;
    }

    @Override protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse)
        throws Exception {
        return false;
    }
}
