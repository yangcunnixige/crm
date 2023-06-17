package com.yangnan.crm.rbac.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 路由显示信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouterMetaVO {
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标
     */
    private String icon;
}