package com.yangnan.crm.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yangnan.crm.common.util.JSONResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JSONResponse {
    public static void out(HttpServletResponse response, JSONResult jsonResult) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), jsonResult);
    }
}