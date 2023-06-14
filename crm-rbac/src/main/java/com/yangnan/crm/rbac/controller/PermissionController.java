package com.yangnan.crm.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yangnan.crm.common.util.JSONResult;
import com.yangnan.crm.rbac.pojo.Permission;
import com.yangnan.crm.rbac.pojo.vo.PermissionVO;
import com.yangnan.crm.rbac.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

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

    @DeleteMapping("/deleteById/{id}")
    public JSONResult deleteById(@PathVariable("id") Long id) {
        boolean isSuccess = permissionService.removeById(id);
        return isSuccess == true ? JSONResult.ok("删除成功") : JSONResult.error("删除失败");
    }

    @PostMapping("/add")
    public JSONResult add(@RequestBody Permission permission) {
        System.out.println("permission: " + permission);
        boolean isSuccess = permissionService.save(permission);
        //boolean isSuccess = userService.saveOrUpdate(user);
        return isSuccess == true ? JSONResult.ok("添加成功") : JSONResult.error("添加失败");
    }

    @GetMapping("/selectById/{id}")
    public JSONResult selectById(@PathVariable("id") Long id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", id);
        Permission permission = permissionService.getOne(wrapper);
        return JSONResult.ok(permission);
    }

    @PutMapping("/update")
    public JSONResult update(@RequestBody Permission permission) {
        boolean isSuccess = permissionService.updateById(permission);
        return isSuccess == true ? JSONResult.ok("更新成功") : JSONResult.error("更新失败");
    }

}