package com.yangnan.crm.rbac.service;

import com.yangnan.crm.bean.pojo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yangnan.crm.common.pojo.User;
import com.yangnan.crm.bean.vo.PermissionVO;

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

    List<PermissionVO> selectRouterListByUserId(User user);

    List<String> selectBtnListByUserId(User user);
}
