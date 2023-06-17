package com.yangnan.crm.common.exception;

import com.yangnan.crm.common.util.JSONResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {

    //全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JSONResult error(Exception e) {
        System.out.println("全局....");
        e.printStackTrace();
        return JSONResult.error(e.getMessage());
    }

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public JSONResult error(AccessDeniedException e) throws AccessDeniedException {
        return JSONResult.error("没有对应的权限");
    }
}