package com.jxy.study.filters;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.web.filter.AccessControlFilter;

/**
 * @description
 * @author: jxy
 * @create: 2019-07-26 08:59
 */
public class ShiroFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o)
        throws Exception {
        System.out.println("我在最后过滤");
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}
