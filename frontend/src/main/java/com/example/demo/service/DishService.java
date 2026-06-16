// DishService.java
package com.example.demo.service;

import com.example.demo.entity.Dish;
import com.example.demo.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DishService {
    @Autowired
    private DishMapper dishMapper;

    public String addDish(Dish dish) { return dishMapper.insertDish(dish) > 0 ? "SUCCESS" : "FAIL"; }
    public List<Dish> getUserMenu(Integer storeId) { return dishMapper.findAvailableByStoreId(storeId); }
    public List<Dish> getMerchantMenu(Integer storeId) { return dishMapper.findAllByStoreId(storeId); }
    public String updateDish(Dish dish) { return dishMapper.updateDish(dish) > 0 ? "SUCCESS" : "FAIL"; }
}