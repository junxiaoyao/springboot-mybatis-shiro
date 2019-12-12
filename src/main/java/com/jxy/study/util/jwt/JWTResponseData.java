package com.jxy.study.util.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: jxy
 * @Date: 2019/9/25 20:09
 * @Description: http返回结果包装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponseData {
    private int code;
    //data
    private Object data;
    private String msg;
    private String token;
}
