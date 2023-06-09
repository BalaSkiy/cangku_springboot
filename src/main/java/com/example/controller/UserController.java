package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.QueryPageParam;
import com.example.common.Result;
import com.example.entity.User;
import com.example.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ch
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        List list = userService.lambdaQuery()
                .eq(User::getId, user.getId())
                .eq(User::getPassword, user.getPassword()).list();

        return list.size() > 0 ? Result.success(list.get(0)) : Result.fail();
    }

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/findById")
    public Result findById(@RequestParam String id) {
        List list = userService.lambdaQuery().eq(User::getId, id).list();
        return list.size() > 0 ? Result.success(list) : Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user) {

        return userService.save(user) ? Result.success() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {

        return userService.updateById(user) ? Result.success() : Result.fail();
    }

    //删除
    @GetMapping("/delete")
    public Result del(@RequestParam String id) {
        return userService.removeById(id) ? Result.success() : Result.fail();
    }

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    //新增或修改
    @GetMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }


    //查询（模糊、匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(user.getName())) {
            lambdaQueryWrapper.like(User::getName, user.getName());
        }

        return Result.success(userService.list(lambdaQueryWrapper));
    }

    @GetMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query) {
//        System.out.println(query);
//        System.out.println("num==="+query.getPageNum());
//        System.out.println("size==="+query.getPageSize());
        HashMap param = query.getParam();
        String name = (String) param.get("name");
//        System.out.println("name==="+(String)param.get("name"));
//        System.out.println("num==="+(String)param.get("num"));

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(User::getName, name);

        IPage result = userService.page(page, lambdaQueryWrapper);
        System.out.println("total==" + result.getTotal());
        return result.getRecords();
    }

    @PostMapping("/listPageC")
    public Result listPageC(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String sex = (String) param.get("sex");


        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(User::getName, name);
        }
        if (StringUtils.isNotBlank(sex)) {
            lambdaQueryWrapper.eq(User::getSex, sex);
        }

        IPage result = userService.page(page, lambdaQueryWrapper);
        System.out.println("total==" + result.getTotal());
        return Result.success(result.getRecords(), result.getTotal());
    }

}
