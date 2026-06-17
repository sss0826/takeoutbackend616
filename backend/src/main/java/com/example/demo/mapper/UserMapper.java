package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // 💡 确保这个方法名叫 findByUsername，必须和 UserService 第 13 行、26 行一模一样！
    @Select("SELECT * FROM order_user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    // 💡 确保这个方法名叫 insert，必须和 UserService 第 17 行一模一样！
    @Insert("INSERT INTO order_user(username, password, phone, role, address, shop_name, vehicle) " +
            "VALUES(#{username}, #{password}, #{phone}, #{role}, #{address}, #{shopName}, #{vehicle})")
    int insert(User user);
}