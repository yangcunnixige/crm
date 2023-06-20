package com.yangnan.crm.security.exception;

import com.yangnan.crm.common.util.JSONResult;
import com.yangnan.crm.security.util.JSONResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("SimpleAccessDeniedHandler.handle");
        JSONResponse.out(response, JSONResult.error("没有对应的权限22"));
    }
}