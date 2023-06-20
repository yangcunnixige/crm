package com.yangnan.crm.rbac.service;

import com.yangnan.crm.bean.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangnan
 * @since 2023-06-12
 */
public interface IRoleService extends IService<Role> {

    Map<String, Object> selectAssignedRole(Long userId);

    void assignRole(Long userId, Long[] roleIds);
}
