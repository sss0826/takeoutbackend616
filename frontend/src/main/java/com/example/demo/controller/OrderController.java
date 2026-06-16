package com.example.demo.controller;

import com.example.demo.entity.FoodOrder;
import com.example.demo.entity.Result;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 4.1 创建订单 (POST /api/order/create)
    @PostMapping("/order/create")
    public Result create(@RequestBody FoodOrder foodOrder) {
        String res = orderService.createOrder(foodOrder);
        if (res.startsWith("SUCCESS:")) {
            Integer orderId = Integer.parseInt(res.split(":")[1]);
            Map<String, Object> data = new HashMap<>();
            data.put("orderId", orderId);
            data.put("orderNo", foodOrder.getOrderNo());
            return Result.success(data);
        }
        return Result.error(500, "创建订单失败");
    }

    // 4.2 模拟支付 (POST /api/order/pay)
    @PostMapping("/order/pay")
    public Result pay(@RequestBody Map<String, Integer> param) {
        Integer orderId = param.get("orderId");
        orderService.updateStatus(orderId, 1); // 0待支付 -> 1已支付
        return Result.success("支付成功");
    }

    // 4.3 获取订单列表 (GET /api/order/list)
    @GetMapping("/order/list")
    public Result getOrderList(@RequestParam(required = false) Integer userId,
                               @RequestParam(required = false) Integer storeId) {
        if (userId != null) {
            return Result.success(orderService.getCustomerOrders(userId));
        } else if (storeId != null) {
            return Result.success(orderService.getMerchantOrders(storeId));
        }
        return Result.error(400, "缺少查询参数");
    }

    // 4.4 更新订单状态 (PUT /api/order/status) -> 商家接单、呼叫配送等通用
    @PutMapping("/order/status")
    public Result updateStatus(@RequestBody Map<String, Integer> param) {
        Integer orderId = param.get("orderId");
        Integer status = param.get("status");
        orderService.updateStatus(orderId, status);
        return Result.success();
    }

    // 6.1 骑手获取待配送列表 (GET /api/rider/orders?status=1)
    @GetMapping("/rider/orders")
    public Result getRiderOrders(@RequestParam Integer status) {
        // 迎合前端传入的 status (1=待接单, 2=配送中, 3=已完成)
        // 传递一个虚拟骑手ID 1 去查配送大厅
        return Result.success(orderService.getRiderOrders(1));
    }

    // 6.2 骑手接单 (POST /api/rider/accept)
    @PostMapping("/rider/accept")
    public Result riderAccept(@RequestBody Map<String, Integer> param) {
        Integer orderId = param.get("orderId");
        Integer riderId = param.get("riderId");
        orderService.riderAccept(orderId, riderId);
        return Result.success();
    }

    // 6.3 骑手完成配送 (POST /api/rider/complete)
    @PostMapping("/rider/complete")
    public Result riderComplete(@RequestBody Map<String, Integer> param) {
        Integer orderId = param.get("orderId");
        orderService.updateStatus(orderId, 4); // 4 代表已送达完成
        return Result.success();
    }
}