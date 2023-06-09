package com.yangnan.crm.rbac.service.impl;

import com.yangnan.crm.rbac.pojo.Students;
import com.yangnan.crm.rbac.mapper.StudentsMapper;
import com.yangnan.crm.rbac.service.IStudentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangnan
 * @since 2023-06-09
 */
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Students> implements IStudentsService {

}
