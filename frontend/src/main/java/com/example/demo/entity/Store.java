package com.example.demo.entity;

import java.math.BigDecimal;

public class Store {
    private Integer id;
    private String name;          // 对应前端文档中的 name (店名)
    private String foodType;      // 对应前端文档中的 foodType (菜品类型)
    private BigDecimal rating;    // 评分
    private Integer sales;        // 销量
    private Integer time;         // 配送时间
    private BigDecimal minPrice;  // 起送价
    private String img;           // 门店图片网址
    private String address;       // 门店地址
    private String phone;         // 商家电话
    private String hours;         // 营业时间
    private Integer ownerId;      // 商家用户ID (绑定注册时的商家角色)

    // 无参构造函数
    public Store() {}

    // Get/Set 方法
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFoodType() { return foodType; }
    public void setFoodType(String foodType) { this.foodType = foodType; }
    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }
    public Integer getSales() { return sales; }
    public void setSales(Integer sales) { this.sales = sales; }
    public Integer getTime() { return time; }
    public void setTime(Integer time) { this.time = time; }
    public BigDecimal getMinPrice() { return minPrice; }
    public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }
    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getHours() { return hours; }
    public void setHours(String hours) { this.hours = hours; }
    public Integer getOwnerId() { return ownerId; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }
}