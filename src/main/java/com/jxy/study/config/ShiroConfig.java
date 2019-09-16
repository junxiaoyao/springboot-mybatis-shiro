package com.jxy.study.config;

import com.jxy.study.util.PermissionFilter;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description
 * @author: jxy
 * @create: 2019-06-11 09:08
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private MyFilterMapUtils filterMapUtils;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);
        // 过滤器连接
        Map<String, String> map = new LinkedHashMap<String, String>();
        // Map<String, Filter> filterMapNow = new LinkedHashMap<>();
        Map<String, Filter> filterMap = filterMapUtils.getFilterMap();
        map.putAll(filterMapUtils.getUrlFilterMap());
        // map.put("/getRoleById", "anno");
        // 登出
        map.put("/logout", "logout");
        // map.put("/rest/getTime","perms");
        map.put("/rest/**", "shiroFilter");
        filterMap.put("my_perm", new PermissionFilter());
        factoryBean.setFilterChainDefinitionMap(map);
        factoryBean.setFilters(filterMap);
        factoryBean.setLoginUrl("/login");
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        return manager;
    }

    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }
}
