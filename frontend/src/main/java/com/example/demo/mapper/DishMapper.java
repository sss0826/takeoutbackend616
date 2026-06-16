// DishMapper.java
package com.example.demo.mapper;

import com.example.demo.entity.Dish;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface DishMapper {
    @Insert("INSERT INTO dish(store_id, dish_name, price, description, is_available) " +
            "VALUES(#{storeId}, #{dishName}, #{price}, #{description}, #{isAvailable})")
    int insertDish(Dish dish);

    @Select("SELECT * FROM dish WHERE store_id = #{storeId} AND is_available = 1")
    List<Dish> findAvailableByStoreId(Integer storeId);

    @Select("SELECT * FROM dish WHERE store_id = #{storeId}")
    List<Dish> findAllByStoreId(Integer storeId);

    @Update("UPDATE dish SET dish_name=#{dishName}, price=#{price}, description=#{description}, is_available=#{isAvailable} WHERE id=#{id}")
    int updateDish(Dish dish);

    @Select("SELECT * FROM dish WHERE id = #{id}")
    Dish findById(Integer id);
}