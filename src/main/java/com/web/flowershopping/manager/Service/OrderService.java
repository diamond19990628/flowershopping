package com.web.flowershopping.manager.Service;

import org.springframework.stereotype.Service;

import com.web.flowershopping.manager.Entity.Result;

@Service
public interface OrderService {
    public Result selectAllOrder(String searchString,Integer status_id,boolean is_today_order);

    public Result changeOrderStatus(Integer status_id,Integer order_id);
}
