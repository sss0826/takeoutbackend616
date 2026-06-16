package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.Result;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 2.1 用户注册 (POST /api/user/register)
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        System.out.println("前端传过来的用户名: " + user.getUsername());
        System.out.println("前端传过来的密码: " + user.getPassword());
        String res = userService.register(user);
        if ("SUCCESS".equals(res)) {
            return Result.success("注册成功");
        }
        return Result.error(400, res);
    }

    // 2.2 用户登录 (POST /api/user/login)
    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        // 1. 调用 Service 去数据库查这个人
        User loginUser = userService.login(user.getUsername(), user.getPassword());

        if (loginUser != null) {
            // 💡 核心：把带有 role、shopName 属性的完整用户对象返回给前端
            // 如果你之前封装了 Result.success(loginUser)，就按照你之前的来
            return loginUser;
        } else {
            // 返回错误状态码，触发前端的 catch
            throw new RuntimeException("用户名或密码错误");
        }
    }
}