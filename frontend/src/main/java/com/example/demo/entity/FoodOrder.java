package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.List;

public class FoodOrder {
    private Integer id;
    private String orderNo;       // 自动生成的流水订单号
    private Integer userId;       // 下单用户ID
    private Integer storeId;      // 门店ID
    private String storeName;     // 门店名称 (冗余备份，方便前端直接展示)
    private BigDecimal totalAmount;// 订单总金额
    private Integer status;       // 状态：0-待支付, 1-已支付/待接单, 2-制作中, 3-配送中, 4-已完成
    private String address;       // 送餐地址
    private String createTime;    // 下单时间
    private Integer deliverId;    // 抢单骑手的ID

    // 核心：一对多关联，接收前端传过来的购物车明细
    private List<OrderItem> items;

    public FoodOrder() {}

    // Get/Set 方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getStoreId() { return storeId; }
    public void setStoreId(Integer storeId) { this.storeId = storeId; }
    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }
    public Integer getDeliverId() { return deliverId; }
    public void setDeliverId(Integer deliverId) { this.deliverId = deliverId; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}