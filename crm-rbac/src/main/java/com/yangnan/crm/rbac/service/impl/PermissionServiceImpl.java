package com.yangnan.crm.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangnan.crm.bean.pojo.Permission;
import com.yangnan.crm.rbac.mapper.PermissionMapper;
import com.yangnan.crm.bean.pojo.RolePermission;
import com.yangnan.crm.common.pojo.User;
import com.yangnan.crm.bean.vo.PermissionVO;
import com.yangnan.crm.rbac.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangnan.crm.rbac.service.IRolePermissionService;
import com.yangnan.crm.rbac.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired(required=false)
    //private PermissionMapper permissionMapper;
    private IRolePermissionService rolePermissionService;

    @Override
    public List<PermissionVO> selectAll() {
        //1.查找权限表中所有的权限
        List<Permission> permissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("sort"));
        List<PermissionVO> permissionVOList = ConvertUtil.convertList(permissionList, PermissionVO.class);
        //2.按照父子关系构件树形结构
        List<PermissionVO> treeList = buildTree(permissionVOList);
        return treeList;
    }

    @Override
    public Map<String, Object> selectAssignedRole(Long roleId) {
        List<PermissionVO> permissionVOList = selectAll();
        //根据角色roleId查询这个角色下面所有的权限
        //List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", roleId).select("permission_id"));
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId).select("permission_id");
        List<RolePermission> rolePermissionList = rolePermissionService.list(queryWrapper);
        List<Long> assignedPermissionIdList = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissionList) {
            assignedPermissionIdList.add(rolePermission.getPermissionId());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("permissionVOList", permissionVOList);
        map.put("assignedPermissionIdList", assignedPermissionIdList);
        return map;
    }

    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
        //在role_permission表里面先删除roleId分配的权限
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        //给这个角色分配权限
        List<RolePermission> rolePermissionList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }

    private List<PermissionVO> buildTree(List<PermissionVO> permissionVOList) {
        List<PermissionVO> treeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(permissionVOList)) {
            for (PermissionVO permissionVO : permissionVOList) {
                //递归的入口pid=0
                if (permissionVO.getPid() == 0) {
                    permissionVO.setChildren(buildChildTree(permissionVO, permissionVOList));
                    treeList.add(permissionVO);
                }
            }
        }

        return treeList;
    }

    private List<PermissionVO> buildChildTree(PermissionVO permissionVO, List<PermissionVO> permissionVOList) {
        List<PermissionVO> childTree = new ArrayList<>();
        if (!CollectionUtils.isEmpty(permissionVOList)) {
            for (PermissionVO perm : permissionVOList) {
                if (perm.getPid().longValue() == permissionVO.getId().longValue()) {
                    //添加孩子的时候，首先要把这个孩子的孩子的集合构造出来
                    perm.setChildren(buildChildTree(perm, permissionVOList));
                    childTree.add(perm);
                }
            }
        }
        return childTree;
    }

    @Override
    public List<PermissionVO> selectRouterListByUserId(User user) {
        List<Permission> permissionList = null;
        if (user.getName().equalsIgnoreCase("admin")) {
            permissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("sort"));
        } else {
            //查询分配给这个用户的所有权限
            permissionList = baseMapper.selectPermissionByUserId(user.getId());
        }
        //转换成树形结构
        List<PermissionVO> permissionVOList = ConvertUtil.convertList(permissionList, PermissionVO.class);
        List<PermissionVO> treeList = buildTree(permissionVOList);
        //转换成前端路由需要的格式
        return treeList;
    }

    @Override
    public List<String> selectBtnListByUserId(User user) {
        List<Permission> permissionList = null;
        if (user.getName().equalsIgnoreCase("admin")) {
            permissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("sort"));
        } else {
            //查询分配给这个用户的所有权限
            permissionList = baseMapper.selectPermissionByUserId(user.getId());
        }
        List<String> btnList = new ArrayList<>();
        for (Permission permission : permissionList) {
            if (permission.getType() == 2) {
                btnList.add(permission.getPermissionValue());
            }
        }
        return btnList;
    }

    public static void main(String[] args) {
        // Long类型比较大小:1、调用longValue 2、使用equals
        Long num1 = 21212323232L;
        Long num2 = 21212323232L;
        System.out.println(num1 == num2);//false
        System.out.println(num1.longValue() == num2.longValue());//true
        System.out.println(num1.equals(num2));//true
    }

}