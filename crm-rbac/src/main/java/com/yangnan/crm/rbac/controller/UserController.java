package com.yangnan.crm.rbac.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yangnan.crm.common.util.JwtUtils;
import com.yangnan.crm.common.util.MD5Util;
import com.yangnan.crm.rbac.pojo.User;
import com.yangnan.crm.rbac.pojo.vo.UserLoginVO;
import com.yangnan.crm.rbac.service.IUserService;
import com.yangnan.crm.common.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yangnan
 * @since 2023-06-08
 */
//tags:可以当做这个组的名字
@Api(tags = "用户管理")
@RestController
@RequestMapping("/rbac/user")
public class UserController {
    @Autowired
    private IUserService userService;


    @PostMapping("/login")
    public JSONResult login(@RequestBody UserLoginVO userLoginVO) {
        User user = userService.selectByUsername(userLoginVO.getUsername());
        if (user == null) {
            return JSONResult.error("用户名不存在");
        }
        //判断密码是不是一致
        String md5Password = MD5Util.MD5Encode(userLoginVO.getPassword());
        if (!user.getPassword().equalsIgnoreCase(md5Password)) {
            return JSONResult.error("密码不正确");
        }

        //判断用户是不是禁用
        if (user.getStatus() == 0) {
            return JSONResult.error("用户已经被禁用");
        }

        //如果能执行到这里，证明username和password登录成功
        String token = JwtUtils.createToken(user.getId(), user.getName());

        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        System.out.println("UserController.login");
        return JSONResult.ok(map);
    }

    @GetMapping("/info")
    public JSONResult info(HttpServletRequest request) {
        //获得请求头token字符串
        String token = request.getHeader("token");
        //从token字符串拿到用户名
        String username = JwtUtils.getUsername(token);
        //根据用户名获取用户信息（基本信息、目录权限和菜单权限组成的树形结构、按钮权限）
        Map<String, Object> map = userService.selectUserPermissionInfo(username);
        //map.put("name", "admin");
        //map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return JSONResult.ok(map);
    }

    @PostMapping("/logout")
    public JSONResult logout() {
        System.out.println("UserController.logout");
        return JSONResult.ok("退出成功");
    }

    @ApiOperation(value = "分页", notes = "根据page第几页，limit每页有多少条，查询当前页的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "第几页", required = false, defaultValue = "1"),
            @ApiImplicitParam(name = "limit", value = "每页有多少条数据", required = false, defaultValue = "5"),
    })
    @GetMapping("/selectByPage")
    public JSONResult selectByPage(@RequestParam(defaultValue = "1") Integer page, Integer limit) {
        IPage<User> iPage = new Page<>(page, limit);
        userService.page(iPage);
        Map<String, Object> map = new HashMap<>();
        map.put("total", iPage.getTotal());
        map.put("list", iPage.getRecords());
        return JSONResult.ok(map);
    }

    @ApiOperation(value = "查询所有", notes = "查询所有页的数据")
    @GetMapping("/selectAll")
    public JSONResult selectAll() {
        List<User> list = userService.list();
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
        boolean isSuccess = userService.removeById(id);
        return isSuccess == true ? JSONResult.ok("删除成功") : JSONResult.error("删除失败");
    }

    @PostMapping("/add")
    public JSONResult add(@RequestBody User user) {
        System.out.println("user: " + user);
        boolean isSuccess = userService.save(user);
        return isSuccess == true ? JSONResult.ok("添加成功") : JSONResult.error("添加失败");
    }

    @GetMapping("/selectById/{id}")
    public JSONResult selectById(@PathVariable("id") Long id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", id);
        User user = userService.getOne(wrapper);
        return JSONResult.ok(user);
    }

    @PutMapping("/update")
    public JSONResult update(@RequestBody User user) {
        boolean isSuccess = userService.updateById(user);
        return isSuccess == true ? JSONResult.ok("更新成功") : JSONResult.error("更新失败");
    }

    @PutMapping("/updateStatus/{id}/{status}")
    public JSONResult updateStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        boolean isSuccess = userService.updateById(user);
        return isSuccess == true ? JSONResult.ok("更新成功") : JSONResult.error("更新失败");
    }

}
