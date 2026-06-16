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
        String res = userService.register(user);
        if ("SUCCESS".equals(res)) {
            return Result.success("注册成功");
        }
        return Result.error(400, res);
    }

    // 2.2 用户登录 (POST /api/user/login)
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        // 调用 service 层进行登录校验
        User loginUser = userService.login(user.getName(), user.getPassword());
        if (loginUser != null) {
            loginUser.setToken("mock_token_abcdefg123456"); // 给前端返回一个模拟 token
            return Result.success(loginUser);
        }
        return Result.error(400, "用户名或密码错误，登录失败！");
    }
}