package com.jxy.study.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description 读取配置
 * @author: jxy
 * @create: 2019-09-17 09:04
 */
@Component
@ConfigurationProperties(prefix = "project")
@Data
public class PropertiesUtil {

    private String loginUrl;

    private String loginSuccess;

    private int cookie_time;
}
