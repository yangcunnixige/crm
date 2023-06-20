package com.yangnan.crm.bean.pojo;

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
 * @since 2023-06-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Students implements Serializable {


    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.NONE)
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    @TableField("true_name")
    private String trueName;

    /**
     * 邮件
     */
    private String email;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户签名
     */
    private String token;

    /**
     * 状态（1：正常 0：停用）
     */
    private Integer status;

    /**
     * 逻辑删除 0未删除， 1已删除
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
