package com.web.flowershopping.Service;

import org.springframework.stereotype.Service;

import com.web.flowershopping.Entity.Result;

@Service
public interface OrderService {
    public Result selectAllOrder(String searchString,Integer status_id,boolean is_today_order);

    public Result changeOrderStatus(Integer status_id,Integer order_id);
}
