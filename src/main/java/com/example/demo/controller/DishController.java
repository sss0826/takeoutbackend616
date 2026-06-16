package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/dish")
@CrossOrigin // 防御跨域问题
public class DishController {

    // 对应前端：浏览点餐时获取所有菜品
    @GetMapping("/list")
    public List<Object> getAllDishes() {
        // 调用你的 dishService.list();
        return null;
    }

    // 对应前端管理后台：上架新菜品
    @PostMapping("/add")
    public String addDish(@RequestBody Object dish) {
        // 调用你的 dishService.save(dish);
        return "上架成功";
    }
}