package com.example.demo.entity;

public class User {
    private Integer id;
    private String name;
    private String password;
    private String address;
    private String phonr;
    private Integer role;//0-普通用户，1-商家，2-配送员
    private String token;

    public Integer getId(){ return id;}
    public void setId(Integer id){ this.id=id;}
    public String getName(){ return name;}
    public void setName(String name){ this.name=name;}
    public String getPassword(){ return password;}
    public void setPassword(String password){ this.password=password;}
    public String getAddress(){ return address;}
    public void setAddress(String address){ this.address=address;}
    public String getPhonr(){ return phonr;}
    public void setPhonr(String phonr){ this.phonr=phonr;}
    public Integer getRole(){ return role;}
    public void setRole(Integer role){ this.role=role;}
    public String getToken(){ return token;}
    public void setToken(String token){ this.token=token;}


}
