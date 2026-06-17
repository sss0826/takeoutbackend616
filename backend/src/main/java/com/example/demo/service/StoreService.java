// StoreService.java
package com.example.demo.service;

import com.example.demo.entity.Store;
import com.example.demo.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreService {
    @Autowired
    private StoreMapper storeMapper;

    public List<Store> getAllStores() { return storeMapper.findAllStores(); }
    public Store getStoreByOwner(Integer ownerId) { return storeMapper.findByOwnerId(ownerId); }
}