package com.web.flowershopping.manager.Service;

import org.springframework.stereotype.Service;

import com.web.flowershopping.manager.Entity.Result;

@Service
public interface OrderService {
    public Result selectAllOrder(String searchString,Integer status_id);
}
