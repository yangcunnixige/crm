package com.yangnan.crm.security.filter;

import com.yangnan.crm.common.util.JwtUtils;
import com.yangnan.crm.security.custom.CustomUserDetails;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 定义JWT认证过滤器
 * OncePerRequestFilter保证每个请求都经过过滤器一次
 * 校验：（已经登录之后，携带token）
 * 1、获取Token
 * 2、解析Token获取其中的userid
 * 3、从Redis中获取用户信息
 * 4、存入Security ContextHolder中
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("uri: " + request.getRequestURI());
        //1、获取Token
        String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)) {
            //没有token，放行，这个filter就是解析token,后面还有别的filter
            filterChain.doFilter(request, response);
            return;
        }
        //2、解析Token获取其中的userid
        String username = JwtUtils.getUsername(token);
        //3、从Redis中获取用户信息
        CustomUserDetails customUserDetails = (CustomUserDetails) redisTemplate.opsForHash().get("login", username);
        if (customUserDetails == null) {
            throw new RuntimeException("token超时,请重新登录");
        }
        //4、存入Security ContextHolder中
        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(customUserDetails, customUserDetails.getPassword(), customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行
        filterChain.doFilter(request, response);
    }
}