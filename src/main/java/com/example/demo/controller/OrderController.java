package com.example.demo.controller;

class User {
    private Integer id;
    private String username; // 牢记之前的教训，用 username 绝不用 name
    private String password;
    private String phone;

    // 💡 必须新加这三个属性，对应数据库和新前端！
    private String role;       // 对应前端的 role
    private String address;    // 买家送餐地址
    private String shopName;   // 商家店铺名 (注意小驼峰命名！)
    private String vehicle;    // 骑手交通工具

    // ==================== 下面是手动生成的 Getter 和 Setter ====================
    // 💡 记得在 IDEA 里右键 -> Generate -> Getter and Setter，把这几个新属性的勾选上生成出来！

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }

    public String getVehicle() { return vehicle; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }
}