package com.jxy.study.config;

import com.jxy.study.filters.LoginFilter;
import com.jxy.study.filters.PermissionFilter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.springframework.stereotype.Component;

/**
 * @description
 * @author: jxy
 * @create: 2019-09-18 09:07
 */
@Component
public class CustomizeFilterImp implements CustomizeFilterInter {

    Map<String, Filter> filterMap = new HashMap<>();

    /*
          由于自定义的filter是在部分其他filter实列化之后实例化的，
          导致shiro无法正常管理该filter
          所以会出现上面filter无法找到SercurityManager的异常信息。
          因此可以采用手动实例化注入shiro
           */
    @Override
    public Map<String, Filter> getCustomizeFilters() {
        filterMap.put("perm", new PermissionFilter());
        filterMap.put("login", new LoginFilter());
        return filterMap;
    }
}
