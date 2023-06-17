package com.yangnan.crm.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangnan.crm.common.pojo.User;
import com.yangnan.crm.rbac.mapper.UserMapper;
import com.yangnan.crm.rbac.pojo.vo.PermissionVO;
import com.yangnan.crm.rbac.pojo.vo.RouterVO;
import com.yangnan.crm.rbac.service.IPermissionService;
import com.yangnan.crm.rbac.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangnan.crm.rbac.util.RouterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IPermissionService permissionService;

    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("name", username));
    }

    @Override
    public Map<String, Object> selectUserPermissionInfo(String username) {
        //根据username查询用户基本信息
        User user = selectByUsername(username);
        //根据userid查询菜单(路由)权限
        List<PermissionVO> permissionVOList =  permissionService.selectRouterListByUserId(user);
        List<RouterVO> routerVOList = RouterUtils.build(permissionVOList);
        //根据userid查询按钮权限

        List<String> btnList = permissionService.selectBtnListByUserId(user);
        Map<String, Object> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("avatar", user.getAvatar());
        map.put("routers", routerVOList);
        map.put("btns", btnList);
        return map;
    }
}
