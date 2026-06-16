package com.example.demo.entity;

public class User {
    private String username;
    private String password;
    private String phone;
    private String address;
    private String role;
    private String token;
    // ==================== 确保属性声明长这样（注意大小写） ====================
    private String shopName;   // 店铺名称
    private String vehicle;    // 交通工具


// ==================== 严格把下面这 4 个方法复制到类最底部 ====================

    public String getShopName() {
        return this.shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
    // 无参构造方法
    public User() {}

    // 手写所有的 Getter 和 Setter 方法
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getToken() {
        return token;

    }
    public void setToken(String token) {}
}