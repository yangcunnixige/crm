package com.yangnan.crm.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangnan.crm.common.pojo.UserLoginVO;
import com.yangnan.crm.common.util.JSONResult;
import com.yangnan.crm.common.util.JwtUtils;
import com.yangnan.crm.security.custom.CustomUserDetails;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private RedisTemplate redisTemplate;

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
        // /rbac/user/login
        //指定登录接口以及提交方式
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/rbac/user/login", "POST"));
    }

    // 获取用户名和密码进行认证，替换原来Controller中/rbac/user/login
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginVO userLoginVO = new ObjectMapper().readValue(request.getInputStream(), UserLoginVO.class);
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(userLoginVO.getUsername(), userLoginVO.getPassword());
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    //认证成功调用
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("CustomUsernamePasswordAuthenticationFilter.successfulAuthentication");
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        //将登录后的用户信息存入redis，username作为key
        redisTemplate.opsForHash().put("login", customUserDetails.getUsername(), customUserDetails);
        //根据id和name生成token，返回给客户端
        String token = JwtUtils.createToken(customUserDetails.getUser().getId(), customUserDetails.getUser().getName());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        responseJson(response, JSONResult.ok("登录失败", map));

    }

    //@ResponseBody
    //认证失败调用
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("CustomUsernamePasswordAuthenticationFilter.unsuccessfulAuthentication");
        responseJson(response, JSONResult.error("登录失败"));
    }

    private void responseJson(HttpServletResponse response, JSONResult jsonResult) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), jsonResult);
    }
}