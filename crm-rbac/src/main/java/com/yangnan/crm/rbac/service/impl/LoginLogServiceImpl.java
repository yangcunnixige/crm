package com.yangnan.crm.rbac.service.impl;

import com.yangnan.crm.bean.pojo.LoginLog;
import com.yangnan.crm.rbac.mapper.LoginLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangnan.crm.security.service.ILoginLogService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yangnan.crm.bean.pojo.LoginLog;
import com.yangnan.crm.rbac.mapper.LoginLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangnan.crm.security.service.ILoginLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangnan
 * @since 2023-06-20
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements ILoginLogService {
    @Override
    public void recordLoginLog(String name, Integer status, String ip, String message) {
        LoginLog loginLog = new LoginLog();
        loginLog.setName(name);
        loginLog.setStatus(status);
        loginLog.setIp(ip);
        loginLog.setMsg(message);
        baseMapper.insert(loginLog);
    }
}
