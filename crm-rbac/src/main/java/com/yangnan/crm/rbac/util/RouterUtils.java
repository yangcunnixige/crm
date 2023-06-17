package com.yangnan.crm.rbac.util;

import com.yangnan.crm.rbac.pojo.vo.PermissionVO;
import com.yangnan.crm.rbac.pojo.vo.RouterMetaVO;
import com.yangnan.crm.rbac.pojo.vo.RouterVO;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 *根据菜单数据构建路由的工具类
 */
public class RouterUtils {

    public static List<RouterVO> build(List<PermissionVO> permissionVOList) {
        List<RouterVO> routers = new LinkedList<RouterVO>();
        for (PermissionVO permissionVO : permissionVOList) {
            RouterVO routerVO = new RouterVO();
            routerVO.setHidden(false);
            routerVO.setAlwaysShow(false);
            routerVO.setPath(getMenuPath(permissionVO));
            routerVO.setComponent(permissionVO.getComponent());
            routerVO.setMeta(new RouterMetaVO(permissionVO.getName(), permissionVO.getIcon()));
            List<PermissionVO> children = permissionVO.getChildren();
            // type=1是菜单，下面是按钮权限，不需要递归遍历了
            if (!CollectionUtils.isEmpty(children) && permissionVO.getType() != 1) {
                routerVO.setAlwaysShow(true);
                //递归
                routerVO.setChildren(build(children));
            }
            routers.add(routerVO);
        }
        return routers;
    }

    /**
     * 获取路由地址
     * @param permissionVO 菜单信息
     * @return 路由地址
     */
    public static String getMenuPath(PermissionVO permissionVO) {
        // 路由中：目录（最顶级的前面有斜杠/rbac）
        // 菜单中没有斜杠user/list
        // 添加的时候是统一没有加，这里给目录加上
        if (permissionVO.getPid().intValue() == 0) {
            return "/" + permissionVO.getPath();
        } else {
            return permissionVO.getPath();
        }
    }
}