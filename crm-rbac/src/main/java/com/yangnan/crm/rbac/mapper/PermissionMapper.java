package com.yangnan.crm.rbac.mapper;

import com.yangnan.crm.bean.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangnan
 * @since 2023-06-12
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> selectPermissionByUserId(Long userId);

}
