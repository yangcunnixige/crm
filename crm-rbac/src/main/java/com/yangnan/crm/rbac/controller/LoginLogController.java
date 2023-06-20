package com.yangnan.crm.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangnan.crm.bean.pojo.LoginLog;
import com.yangnan.crm.bean.query.LoginLogQuery;
import com.yangnan.crm.common.util.JSONResult;
import com.yangnan.crm.security.service.ILoginLogService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangnan
 * @since 2023-06-20
 */
@RestController
@RequestMapping("/rbac/loginLog")
public class LoginLogController {
    @Autowired
    private ILoginLogService loginLogService;

    @ApiOperation(value = "分页", notes = "根据page第几页，limit每页有多少条，查询当前页的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = false, defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页有多少条数据", required = false, defaultValue = "5"),
    })
    //http://localhost:9528/rbac/loginLog/selectByPage?page=1&limit=5&name=admin&status=0
    @GetMapping("/selectByPage")
    public JSONResult selectByPage(@RequestParam(defaultValue = "1") Integer page, Integer limit, LoginLogQuery loginLogQuery) {
        IPage<LoginLog> iPage = new Page<>(page, limit);
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(loginLogQuery.getName())) {
            queryWrapper.like("name", loginLogQuery.getName());
        }
        if (!ObjectUtils.isEmpty(loginLogQuery.getIp())) {
            queryWrapper.like("ip",  loginLogQuery.getIp());
        }
        if (!ObjectUtils.isEmpty(loginLogQuery.getAccessTimeBegin()) && !ObjectUtils.isEmpty(loginLogQuery.getAccessTimeEnd())) {
            queryWrapper.between("gmt_create", loginLogQuery.getAccessTimeBegin(), loginLogQuery.getAccessTimeEnd());
        }
        loginLogService.page(iPage, queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", iPage.getTotal());
        map.put("list", iPage.getRecords());
        return JSONResult.ok(map);
    }

    @ApiOperation(value = "根据id删除", notes = "根据id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的id值", required = true)
    })
    @PreAuthorize("hasAuthority('log:loginLog:delete')")
    @DeleteMapping("/deleteById/{id}")
    public JSONResult deleteById(@PathVariable("id") Long id) {
        boolean isSuccess = loginLogService.removeById(id);
        return isSuccess == true ? JSONResult.ok("删除成功") : JSONResult.error("删除失败");
    }

    @DeleteMapping("/deleteAll/{ids}")
    public JSONResult deleteAll(@PathVariable("ids") Long[] ids) {
        boolean isSuccess = loginLogService.removeByIds(Arrays.asList(ids));
        return isSuccess == true ? JSONResult.ok("批量删除成功") : JSONResult.error("批量删除失败");
    }

}
