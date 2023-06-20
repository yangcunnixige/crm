package com.yangnan.crm.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangnan.crm.bean.pojo.LoginLog;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Gao
 * @since 2023-06-20
 */
public interface ILoginLogService extends IService<LoginLog> {
    void recordLoginLog(String name, Integer status, String ip, String message);
}