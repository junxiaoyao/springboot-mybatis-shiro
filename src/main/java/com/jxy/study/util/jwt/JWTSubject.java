package com.jxy.study.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: jxy
 * @Date: 2019/9/25 20:02
 * @Description: jwt用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTSubject {
    private String userName;
}
