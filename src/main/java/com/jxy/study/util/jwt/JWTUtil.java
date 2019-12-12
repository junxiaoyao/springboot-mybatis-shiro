package com.jxy.study.util.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @Auther: jxy
 * @Date: 2019/9/25 20:12
 * @Description: jwt工具类
 */
public class JWTUtil {
    //加密/解密密匙
    private final static String JWT_SECRET = "TEST_JWT_KEY";
    private final static ObjectMapper OBJECTmAPPER = new ObjectMapper();
    private final static int JWT_EXPRIE_TIME = 1 * 60 * 1000;
    private final static int JWT_EXPRIE_FAIL = 1006;

    //创建加密解密密匙
    public static SecretKey getSecretKey() {
        SecretKey secretKey = null;
        try {
            byte[] encodeKey = JWT_SECRET.getBytes();
            secretKey = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
        } finally {
            return secretKey;
        }
    }

    /**
     * @param id       token唯一id
     * @param iss      jwt签发者
     * @param subject  jwt面向用户
     * @param ttlMills 过期时间
     * @return token, token 是一次性的。在用户登录有限期内有效，超时或退出及失效。
     */
    public static String createJWTToken(String id, String iss, String subject, long ttlMills) {
        //加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //过期时间
        long nowMills = System.currentTimeMillis();
        Date date = new Date(nowMills);
        SecretKey secretKey = getSecretKey();
        //jwt构建器，使用指定的加密算法和密匙
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)
                .setIssuer(iss)
                .setIssuedAt(date)//签发时间
                .setSubject(subject)
                .signWith(signatureAlgorithm, secretKey);
        if (ttlMills >= 0) {
            Date expireTime = new Date(ttlMills + nowMills);
            jwtBuilder.setExpiration(expireTime);
        }
        //生成token返回
        return jwtBuilder.compact();
    }

    /**
     * 验证token是否有限
     *
     * @param jwtString
     * @return
     */
    public static JWTResult validateJwt(String jwtString) {
        JWTResult jwtResult = new JWTResult();
        Claims claims = null;
        try {
            claims = parseJwt(jwtString);
            jwtResult.setClaim(claims);
            jwtResult.setCode(200);
            jwtResult.setMsg("ok");
            jwtResult.setSuccess(true);
        } catch (ExpiredJwtException e) {
            jwtResult.setCode(201);
            jwtResult.setMsg("toke_time_out");
            jwtResult.setSuccess(false);
        } catch (SignatureException e) {
            jwtResult.setCode(500);
            jwtResult.setMsg("toke_fail");
            jwtResult.setSuccess(false);
        } catch (Exception e) {
            jwtResult.setCode(501);
            jwtResult.setMsg("other_fail");
            jwtResult.setSuccess(false);
        }
        return jwtResult;
    }

    /**
     * 解析token
     *
     * @param jwtString
     * @return
     */
    public static Claims parseJwt(String jwtString) {
        SecretKey secretKey = getSecretKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtString).getBody();
    }

    //将对象转化为json
    public static String generalSubject(Object o) {
        try {
            return OBJECTmAPPER.writeValueAsString(o);
        } catch (Exception e) {
            return "";
        }
    }
}
