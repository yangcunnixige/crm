package com.yangnan.crm.bean.query;

import lombok.Data;

import java.util.Date;

@Data
public class UserQuery {
    private String name;
    private String email;
    private Integer status;
    private Date gmtCreateBegin;
    private Date gmtCreateEnd;
}