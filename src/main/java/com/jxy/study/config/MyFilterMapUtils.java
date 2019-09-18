package com.jxy.study.config;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description
 * @author: jxy
 * @create: 2019-06-10 15:05
 */
@Component
@ConfigurationProperties(prefix = "security.shiro")
@Data
public class MyFilterMapUtils {

    // 路径map
    private Map<String, String> urlFilterMap;

}
