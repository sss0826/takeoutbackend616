package com.example.demo.entity;

import java.math.BigDecimal;

public class Dish {
    private Integer id;
    private Integer storeId;      // 属于哪家门店
    private String DishName;          // 对应前端文档中的 name (菜品名称)
    private BigDecimal price;     // 涉及金钱，严格使用 BigDecimal，防止精度丢失
    private String description;   // 菜品描述
    private String img;           // 对应前端文档中的 img (菜品图片网址)
    private Integer status;       // 对应前端文档中的 status: 0-下架, 1-在售

    // 无参构造函数
    public Dish() {}

    // Get/Set 方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getStoreId() { return storeId; }
    public void setStoreId(Integer storeId) { this.storeId = storeId; }
    public String getDishName() { return DishName; }
    public void setDishName(String name) { this.DishName = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}