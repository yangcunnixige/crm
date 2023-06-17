package com.yangnan.crm.rbac.service.impl;

import com.yangnan.crm.common.pojo.User;
import com.yangnan.crm.rbac.service.IPermissionService;
import com.yangnan.crm.rbac.service.IUserService;
import com.yangnan.crm.security.custom.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImpl.loadUserByUsername");
        //1.查询用户信息
        User user = userService.selectByUsername(username);
        if (user == null) {
            //抛出这个异常会被ExceptionTranslationFilter处理
            throw new UsernameNotFoundException("用户名不存在");
        }
        if (user.getStatus() == 0) {
            throw  new RuntimeException("该用户被禁用");
        }
        //2.查询用户的权限信息
        List<String> permissionList = permissionService.selectBtnListByUserId(user);
        //3.将用户信息和权限信息封装到UserDetail这个接口的实现类
        return new CustomUserDetails(user, permissionList);
    }
}