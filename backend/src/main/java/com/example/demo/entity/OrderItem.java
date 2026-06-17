package com.example.demo.entity;

import java.math.BigDecimal;

public class OrderItem {
    private Integer id;
    private Integer orderId;     // 所属订单的主表ID
    private Integer dishId;      // 对应前端文档中的 dishId
    private String dishName;     // 菜品名称
    private Integer quantity;    // 对应前端文档中的 quantity (购买数量)
    private BigDecimal price;    // 下单时的单价 (防止后续商家改价影响历史账单)

    public OrderItem() {}

    // Get/Set 方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Integer getDishId() { return dishId; }
    public void setDishId(Integer dishId) { this.dishId = dishId; }
    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}