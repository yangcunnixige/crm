package com.yangnan.crm.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangnan.crm.rbac.pojo.Permission;
import com.yangnan.crm.rbac.mapper.PermissionMapper;
import com.yangnan.crm.rbac.pojo.vo.PermissionVO;
import com.yangnan.crm.rbac.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangnan.crm.rbac.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    private PermissionMapper permissionMapper;

    @Override
    public List<PermissionVO> selectAll() {
        //1.查找权限表中所有的权限
        List<Permission> permissionList = permissionMapper.selectList(new QueryWrapper<Permission>().orderByAsc("sort"));
        List<PermissionVO> permissionVOList = ConvertUtil.convertList(permissionList, PermissionVO.class);
        //2.按照父子关系构件树形结构
        List<PermissionVO> treeList = buildTree(permissionVOList);
        return treeList;
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

    public static void main(String[] args) {
        // Long类型比较大小:1、调用longValue 2、使用equals
        Long num1 = 21212323232L;
        Long num2 = 21212323232L;
        System.out.println(num1 == num2);//false
        System.out.println(num1.longValue() == num2.longValue());//true
        System.out.println(num1.equals(num2));//true
    }
}