package com.jxy.study.config;


import com.jxy.study.util.PropertiesUtil;
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
 * @description Shiro配置类
 * @author: jxy
 * @create: 2019-06-11 09:08
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private MyFilterMapUtils filterMapUtils;

    @Autowired
    private PropertiesUtil propertiesUtil;

    @Autowired
    private CustomizeFilterInter customizeFilterInter;

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(manager);
        // 过滤器路径
        Map<String, String> map = new LinkedHashMap<String, String>();
        // 过滤器链表
        Map<String, Filter> filterMap = customizeFilterInter.getCustomizeFilters();
        map.putAll(filterMapUtils.getUrlFilterMap());
        factoryBean.setFilterChainDefinitionMap(map);
        factoryBean.setFilters(filterMap);
        factoryBean.setLoginUrl(propertiesUtil.getLoginUrl());
        factoryBean.setSuccessUrl(propertiesUtil.getLoginSuccess());
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
