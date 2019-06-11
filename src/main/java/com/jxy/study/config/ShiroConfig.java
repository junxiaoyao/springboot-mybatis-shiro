package com.jxy.study.config;

import java.util.LinkedHashMap;
import java.util.Map;
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
  private MyFilterMap filterMap;

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager manager) {
    ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
    factoryBean.setSecurityManager(manager);
    //过滤器
    Map<String, String> map = new LinkedHashMap<String, String>();
    map.putAll(filterMap.getUrlFilterMap());
//    map.put("/getRoleById", "anno");
    //登出
    map.put("/logout", "logout");
    map.put("/rest/getTime","perms");
    factoryBean.setFilterChainDefinitionMap(map);
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
