package com.jxy.study.util.jwt;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

/**
 * @Auther: jxy
 * @Date: 2019/9/25 19:53
 * @Description: 模拟db用户数据
 */
@Data
public class JWTUsersUtil {
    public final static HashMap<String, String> users = new HashMap<>();

    static {
        users.put("user", "123456");
        for (int i = 0; i < 10; i++) {
            users.put("user" + i, "123456");
        }
    }

    public static boolean isLogin(String userName, String password) {
        if (StringUtils.isEmpty(userName)) {
            return false;
        }
        if (StringUtils.isNotEmpty(users.get(userName))) {
            return true;
        }
        return false;
    }
}
