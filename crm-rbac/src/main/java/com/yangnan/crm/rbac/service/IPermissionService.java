package com.yangnan.crm.rbac.service;

import com.yangnan.crm.rbac.pojo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yangnan.crm.rbac.pojo.vo.PermissionVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangnan
 * @since 2023-06-12
 */
public interface IPermissionService extends IService<Permission> {
    List<PermissionVO> selectAll();

    Map<String, Object> selectAssignedRole(Long roleId);

    void assignPermission(Long roleId, Long[] permissionIds);

    List<PermissionVO> selectRouterListByUserId(Long userId);
}
