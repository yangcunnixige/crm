package com.yangnan.crm.rbac.controller;


import com.yangnan.crm.common.util.JSONResult;
import com.yangnan.crm.rbac.pojo.vo.PermissionVO;
import com.yangnan.crm.rbac.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangnan
 * @since 2023-06-12
 */
@RestController
@RequestMapping("/rbac/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/selectAll")
    public JSONResult selectAll() {
        List<PermissionVO> list = permissionService.selectAll();
        return JSONResult.ok(list);
    }
}