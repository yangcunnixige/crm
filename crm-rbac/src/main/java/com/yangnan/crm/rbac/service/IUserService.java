package com.yangnan.crm.rbac.service;

import com.yangnan.crm.common.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangnan
 * @since 2023-06-08
 */
public interface IUserService extends IService<User> {

    User selectByUsername(String username);

    Map<String, Object> selectUserPermissionInfo(String username);

}
