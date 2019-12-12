package com.jxy.study.util.jwt;

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * @Auther: jxy
 * @Date: 2019/9/25 20:05
 * @Description: jwt返回结果实体
 */
@Data
public class JWTResult {
    //状态码
    private int code;
    //请求结果
    private boolean success;
    //token中的payload信息
    private Claims claim;
    //信息
    private String msg;
}
