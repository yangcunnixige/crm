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
 * 角色权限
 * </p>
 *
 * @author yangnan
 * @since 2023-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RolePermission implements Serializable {


    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("role_id")
    private Long roleId;

    @TableField("permission_id")
    private Long permissionId;

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
