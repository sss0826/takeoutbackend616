package com.example.demo.service;

import com.example.demo.entity.FoodOrder;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Dish;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private DishMapper dishMapper;

    @Transactional
    public String createOrder(FoodOrder foodOrder) {
        String orderNo = "ORD_" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
        foodOrder.setOrderNo(orderNo);
        foodOrder.setStatus(0); // 0-待支付

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem item : foodOrder.getItems()) {
            Dish dish = dishMapper.findById(item.getDishId());
            if (dish == null) return "FAIL:商品不存在";
            item.setDishName(dish.getDishName());
            item.setPrice(dish.getPrice());

            BigDecimal itemSum = dish.getPrice().multiply(new BigDecimal(item.getQuantity()));
            totalAmount = totalAmount.add(itemSum);
        }
        foodOrder.setTotalAmount(totalAmount);

        orderMapper.insertOrder(foodOrder);

        for (OrderItem item : foodOrder.getItems()) {
            item.setOrderId(foodOrder.getId());
            orderMapper.insertItem(item);
        }
        return "SUCCESS:" + foodOrder.getId();
    }

    public String updateStatus(Integer id, Integer status) {
        return orderMapper.updateStatus(id, status) > 0 ? "SUCCESS" : "FAIL";
    }

    public String riderAccept(Integer id, Integer riderId) {
        return orderMapper.riderAcceptOrder(id, riderId) > 0 ? "SUCCESS" : "FAIL";
    }

    public List<FoodOrder> getCustomerOrders(Integer userId) {
        return orderMapper.findByUserId(userId);
    }

    public List<FoodOrder> getMerchantOrders(Integer storeId) {
        return orderMapper.findByStoreId(storeId);
    }

    public List<FoodOrder> getRiderOrders(Integer riderId) {
        return orderMapper.findRiderOrders(riderId);
    }
}