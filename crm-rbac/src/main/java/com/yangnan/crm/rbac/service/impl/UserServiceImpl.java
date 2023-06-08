package com.yangnan.crm.rbac.service.impl;

import com.yangnan.crm.rbac.pojo.User;
import com.yangnan.crm.rbac.mapper.UserMapper;
import com.yangnan.crm.rbac.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangnan
 * @since 2023-06-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
