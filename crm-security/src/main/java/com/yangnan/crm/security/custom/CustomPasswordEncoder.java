package com.yangnan.crm.security.custom;

import com.yangnan.crm.common.util.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义密码组件，替换SpringSecurity提供的密码校验
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    /**
     * 传入原始密码进行加密
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.MD5Encode(rawPassword.toString());
    }

    /**
     * 密码比较
     * @param rawPassword 原始密码，没有加密
     * @param encodedPassword 加密之后的密码(数据库中存储的加密后密码)
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equalsIgnoreCase(MD5Util.MD5Encode(rawPassword.toString()));
    }
}