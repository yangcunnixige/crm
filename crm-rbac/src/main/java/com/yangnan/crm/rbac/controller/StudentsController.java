package com.yangnan.crm.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangnan.crm.common.util.JSONResult;
import com.yangnan.crm.rbac.pojo.Students;
import com.yangnan.crm.rbac.service.IStudentsService;
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
 * @since 2023-06-09
 */
//tags:可以当做这个组的名字
@Api(tags = "学生管理")
@RestController
@RequestMapping("/rbac/students")
public class StudentsController {
    @Autowired
    private IStudentsService studentsService;


    @ApiOperation(value = "分页", notes = "根据page第几页，limit每页有多少条，查询当前页的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = false, defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页有多少条数据", required = false, defaultValue = "5"),
    })
    @GetMapping("/selectByPage")
    public JSONResult selectByPage(@RequestParam(defaultValue = "1") Integer page, Integer limit) {
        IPage<Students> iPage = new Page<>(page, limit);
        studentsService.page(iPage);
        Map<String, Object> map = new HashMap<>();
        map.put("total", iPage.getTotal());
        map.put("list", iPage.getRecords());
        return JSONResult.ok(map);
    }

    @ApiOperation(value = "查询所有", notes = "查询所有页的数据")
    @GetMapping("/selectAll")
    public JSONResult selectAll() {
        List<Students> list = studentsService.list();
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
        boolean isSuccess = studentsService.removeById(id);
        return isSuccess == true ? JSONResult.ok("删除成功") : JSONResult.error("删除失败");
    }


    @PostMapping("/add")
    public JSONResult add(@RequestBody Students students) {
        System.out.println("students: " + students);
        boolean isSuccess = studentsService.save(students);
        return isSuccess == true ? JSONResult.ok("添加成功") : JSONResult.error("添加失败");
    }

    @GetMapping("/selectById/{id}")
    public JSONResult selectById(@PathVariable("id") Long id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", id);
        Students students = studentsService.getOne(wrapper);
        return JSONResult.ok(students);
    }

    @PutMapping("/update")
    public JSONResult update(@RequestBody Students students) {
        boolean isSuccess = studentsService.updateById(students);
        return isSuccess == true ? JSONResult.ok("更新成功") : JSONResult.error("更新失败");
    }
}
