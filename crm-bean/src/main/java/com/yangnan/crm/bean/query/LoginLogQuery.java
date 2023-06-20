package com.yangnan.crm.bean.query;

import lombok.Data;

import java.util.Date;

@Data
public class LoginLogQuery {
    private String name;
    private String ip;
    private String msg;
    private Date accessTimeBegin;
    private Date accessTimeEnd;
}