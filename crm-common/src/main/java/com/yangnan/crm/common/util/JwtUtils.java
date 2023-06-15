package com.yangnan.crm.common.util;

import io.jsonwebtoken.*;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * 生成Token令牌的工具类
 */
public class JwtUtils {
    //token过期时间
    private static long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    //加密秘钥
    private static String tokenSignKey = "123456";

    //根据用户id和用户名称生成token字符串
    public static String createToken(Long userId, String username) {
        String token = Jwts.builder()
                .setSubject("RBAC-USER")//JWT头
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))//当前时间+国际时间后失效
                .claim("userId", userId)//有效载荷，重要信息
                .claim("username", username)//有效载荷，重要信息
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)//秘钥
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //从token字符串获取userid
    public static Long getUserId(String token) {
        try {
            if (ObjectUtils.isEmpty(token)) return null;

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Long userId = (Long) claims.get("userId");
            return userId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //从token字符串获取username
    public static String getUsername(String token) {
        try {
            if (ObjectUtils.isEmpty(token)) return "";

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return (String) claims.get("username");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String token = JwtUtils.createToken(1587275356117819394L, "zhangsan");
        System.out.println(token);

        Long userId = JwtUtils.getUserId(token);
        System.out.println(userId);

        String username = JwtUtils.getUsername(token);
        System.out.println(username);
    }
}