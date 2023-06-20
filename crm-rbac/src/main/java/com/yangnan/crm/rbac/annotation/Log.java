package com.yangnan.crm.rbac.annotation;

import com.yangnan.crm.rbac.enums.LogOperType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     * @return
     */
    public String title() default "";

    /**
     * 功能
     * @return
     */
    public LogOperType operType() default LogOperType.OTHER;

    /**
     * 是否保存请求的参数
     * @return
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     * @return
     */
    public boolean isSaveResponseData() default true;
}