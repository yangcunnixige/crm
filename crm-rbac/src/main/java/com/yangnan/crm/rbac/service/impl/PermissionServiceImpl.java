package com.yangnan.crm.rbac.service.impl;

import com.yangnan.crm.rbac.pojo.Permission;
import com.yangnan.crm.rbac.mapper.PermissionMapper;
import com.yangnan.crm.rbac.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
