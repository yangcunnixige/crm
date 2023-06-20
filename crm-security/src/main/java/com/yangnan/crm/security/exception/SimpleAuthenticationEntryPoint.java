package com.yangnan.crm.security.exception;

import com.yangnan.crm.common.util.JSONResult;
import com.yangnan.crm.security.util.JSONResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.out.println("SimpleAuthenticationEntryPoint.commence");
        JSONResponse.out(response, JSONResult.error("认证失败"));
    }
}