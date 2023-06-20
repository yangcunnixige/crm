package com.yangnan.crm.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangnan.crm.bean.pojo.Role;
import com.yangnan.crm.rbac.mapper.RoleMapper;
import com.yangnan.crm.bean.pojo.UserRole;
import com.yangnan.crm.rbac.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangnan.crm.rbac.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangnan
 * @since 2023-06-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public Map<String, Object> selectAssignedRole(Long userId) {
        List<Role> roleList = baseMapper.selectList(null);

        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).select("role_id");
        List<UserRole> assignedUserRoleList = userRoleService.list(queryWrapper);
        List<Long> assignedRoleIdList = new ArrayList<>();
        for (UserRole userRole : assignedUserRoleList) {
            assignedRoleIdList.add(userRole.getRoleId());
        }

        Map<String, Object> map = new HashMap<>();
        //所有角色集合
        map.put("roleList", roleList);
        //这个用户已经分配的角色的id集合
        map.put("assignedRoleIdList", assignedRoleIdList);
        return map;
    }

    @Override
    public void assignRole(Long userId, Long[] roleIds) {
        //在user_role表里面先删除userId分配的角色
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));
        //给这个用户分配角色
        List<UserRole> userRoleList = new ArrayList<>();
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        }
        userRoleService.saveBatch(userRoleList);
    }

}
