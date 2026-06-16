package com.example.demo.controller;

import com.example.demo.entity.Dish;
import com.example.demo.entity.Result;
import com.example.demo.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    // 5.3 获取菜品列表 (GET /api/dish/list?storeId=1)
    @GetMapping("/list")
    public Result list(@RequestParam Integer storeId) {
        return Result.success(dishService.getMerchantMenu(storeId));
    }

    // 5.4 新增菜品 (POST /api/dish/save)
    @PostMapping("/save")
    public Result save(@RequestBody Dish dish) {
        dish.setStatus(1); // 👈 修正这里：由 setIsAvailable(1) 改为 setStatus(1)，默认上架在售
        dishService.addDish(dish);
        return Result.success("新增成功");
    }

    // 5.5 更新菜品 (PUT /api/dish/update)
    @PutMapping("/update")
    public Result update(@RequestBody Dish dish) {
        dishService.updateDish(dish);
        return Result.success("更新成功");
    }
}