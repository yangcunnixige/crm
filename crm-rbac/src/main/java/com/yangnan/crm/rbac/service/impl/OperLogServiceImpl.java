package com.yangnan.crm.rbac.service.impl;

import com.yangnan.crm.bean.pojo.OperLog;
import com.yangnan.crm.rbac.mapper.OperLogMapper;
import com.yangnan.crm.rbac.service.IOperLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author yangnan
 * @since 2023-06-20
 */
@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, OperLog> implements IOperLogService {

}
