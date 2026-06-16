package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface UserMapper {
    @Select("SELECT FROM order_user WHERE username=#{username}")
    User findByUsername(String username);


    @Insert("INSERT INTO order_user(username, password, phone, address, role) " +
            "VALUES(#{username},#{password}, #{phone}, #{address}, #{role})")
    int insert(User user);

}
