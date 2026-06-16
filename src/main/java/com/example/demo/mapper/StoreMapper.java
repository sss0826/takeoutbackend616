// StoreMapper.java
package com.example.demo.mapper;

import com.example.demo.entity.Store;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface StoreMapper {
    @Select("SELECT * FROM store")
    List<Store> findAllStores();

    @Select("SELECT * FROM store WHERE owner_id = #{ownerId} LIMIT 1")
    Store findByOwnerId(Integer ownerId);
}