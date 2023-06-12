package com.yangnan.crm.rbac.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangnan
 * @since 2023-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Permission implements Serializable {


    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 所属上级
     */
    private Long pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型(0:目录,1:菜单,2:按钮)
     */
    private Integer type;

    /**
     * 权限值
     */
    @TableField("permission_value")
    private String permissionValue;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态(1:正常，0:禁止)
     */
    private Integer status;

    /**
     * 逻辑删除 0（true）未删除， 1（false）已删除
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
