package com.yangnan.crm.bean.vo;

import lombok.Data;

import java.util.List;

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
 roles: ['admin','editor']    control the page roles (you can set multiple roles)
 title: 'title'               the name show in sidebar and breadcrumb (recommend set)
 icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
 breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
 activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
 }
 */
@Data
public class RouterVO {
    /**
     * 路由名字 使用MetaVO中name显示
     */
    // private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件地址
     * path是key，component是值
     */
    private String component;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private boolean hidden;

    /**
     * 当一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式
     * 显示折叠的箭头
     */
    private Boolean alwaysShow;

    /**
     * 其他元素
     */
    private RouterMetaVO meta;

    /**
     * 子路由
     */
    private List<RouterVO> children;

}