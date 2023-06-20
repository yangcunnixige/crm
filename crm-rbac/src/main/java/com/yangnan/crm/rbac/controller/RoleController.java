package com.yangnan.crm.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangnan.crm.common.util.JSONResult;
import com.yangnan.crm.bean.pojo.Role;
import com.yangnan.crm.rbac.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangnan
 * @since 2023-06-12
 */
//tags:可以当做这个组的名字
@Api(tags = "角色管理")
@RestController
@RequestMapping("/rbac/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;


    @ApiOperation(value = "分页", notes = "根据page第几页，limit每页有多少条，查询当前页的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = false, defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页有多少条数据", required = false, defaultValue = "5"),
    })
    @GetMapping("/selectByPage")
    public JSONResult selectByPage(@RequestParam(defaultValue = "1") Integer page, Integer limit) {
        IPage<Role> iPage = new Page<>(page, limit);
        roleService.page(iPage);
        Map<String, Object> map = new HashMap<>();
        map.put("total", iPage.getTotal());
        map.put("list", iPage.getRecords());
        return JSONResult.ok(map);
    }

    @ApiOperation(value = "查询所有", notes = "查询所有页的数据")
    @GetMapping("/selectAll")
    public JSONResult selectAll() {
        List<Role> list = roleService.list();
        return JSONResult.ok(list);
    }

    @ApiOperation(value = "删除", notes = "根据当前ID删除数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", required = true)
    })
    //对于swagger，不适用RequestMapping
    //因为RequestMapping支持任意请求方式，swagger会为整个接口生成7种请求方式的文档。
    @DeleteMapping("/deleteById/{id}")
    public JSONResult deleteById(@PathVariable("id") Long id) {
        boolean isSuccess = roleService.removeById(id);
        return isSuccess == true ? JSONResult.ok("删除成功") : JSONResult.error("删除失败");
    }


    @PostMapping("/add")
    public JSONResult add(@RequestBody Role role) {
        System.out.println("students: " + role);
        boolean isSuccess = roleService.save(role);
        return isSuccess == true ? JSONResult.ok("添加成功") : JSONResult.error("添加失败");
    }

    @GetMapping("/selectById/{id}")
    public JSONResult selectById(@PathVariable("id") Long id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", id);
        Role role = roleService.getOne(wrapper);
        return JSONResult.ok(role);
    }

    @PutMapping("/update")
    public JSONResult update(@RequestBody Role role) {
        boolean isSuccess = roleService.updateById(role);
        return isSuccess == true ? JSONResult.ok("更新成功") : JSONResult.error("更新失败");
    }

    @GetMapping("/selectAssignedRole/{userId}")
    public JSONResult selectAssignedRole(@PathVariable("userId") Long userId) {
        Map<String, Object> map = roleService.selectAssignedRole(userId);
        return JSONResult.ok(map);
    }

    @PostMapping("/assignRole")
    public JSONResult assignRole(Long userId, Long[] roleIds) {
        roleService.assignRole(userId, roleIds);
        return JSONResult.ok("分配成功");
    }

}
