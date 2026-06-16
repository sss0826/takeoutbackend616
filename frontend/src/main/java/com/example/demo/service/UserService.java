package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;//引入仓库管理员,以便调用方法查询数据库

    public String register(User user){
        User existingUser=userMapper.findByUsername(user.getName());
        if(existingUser!=null){
            return "用户已存在，注册失败！";
        }
        int row=userMapper.insert(user);
        if(row>0){
            return "注册成功！";
        }
        return "注册失败，服务器未知错误";
    }

    //登录
    public User login(String username,String password){
        User user=userMapper.findByUsername(username);//把试图登录的用户信息查出来

        if(user!=null&&user.getPassword().equals(password)){
            return user;//如果存在则直接返回
        }
        return null;
    }

}
