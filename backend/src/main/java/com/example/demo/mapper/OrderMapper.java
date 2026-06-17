package com.example.demo.mapper;

import com.example.demo.entity.FoodOrder;
import com.example.demo.entity.OrderItem;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO food_order(order_no, user_id, store_id, total_amount, status) " +
            "VALUES(#{orderNo}, #{userId}, #{storeId}, #{totalAmount}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOrder(FoodOrder foodOrder);

    @Insert("INSERT INTO order_item(order_id, dish_id, dish_name, quantity, price) " +
            "VALUES(#{orderId}, #{dishId}, #{dishName}, #{quantity}, #{price})")
    int insertItem(OrderItem item);

    @Update("UPDATE food_order SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    @Update("UPDATE food_order SET status = 3, deliver_id = #{riderId} WHERE id = #{id} AND status = 1")
    int riderAcceptOrder(@Param("id") Integer id, @Param("riderId") Integer riderId);

    @Select("SELECT * FROM food_order WHERE user_id = #{userId} ORDER BY id DESC")
    List<FoodOrder> findByUserId(Integer userId);

    @Select("SELECT * FROM food_order WHERE store_id = #{storeId} ORDER BY id DESC")
    List<FoodOrder> findByStoreId(Integer storeId);

    @Select("SELECT * FROM food_order WHERE status = 3 AND (deliver_id IS NULL OR deliver_id = #{riderId})")
    List<FoodOrder> findRiderOrders(Integer riderId);
}