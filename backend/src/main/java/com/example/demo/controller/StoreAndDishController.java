// 建立 StoreController 处理门店
package com.example.demo.controller;

import com.example.demo.entity.Result;
import com.example.demo.service.StoreService;
import com.example.demo.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class StoreAndDishController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private DishService dishService;

    // 3.1 获取门店列表 (GET /api/store/list)
    @GetMapping("/store/list")
    public Result getStoreList() {
        return Result.success(storeService.getAllStores());
    }

    // 3.2 获取门店详情含菜品 (GET /api/store/detail?storeId=1)
    @GetMapping("/store/detail")
    public Result getStoreDetail(@RequestParam Integer storeId) {
        Map<String, Object> detail = new HashMap<>();
        // 假设这里能查到店名等基本信息，包装成前端要的结构
        detail.put("id", storeId);
        detail.put("name", "系统外卖门店");
        detail.put("dishes", dishService.getUserMenu(storeId)); // 装入菜品列表
        return Result.success(detail);
    }

    // 5.1 获取商家门店信息 (GET /api/merchant/store?merchantId=1)
    @GetMapping("/merchant/store")
    public Result getMerchantStore(@RequestParam Integer merchantId) {
        return Result.success(storeService.getStoreByOwner(merchantId));
    }
}