package com.web.flowershopping.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.web.flowershopping.Entity.OrderItem;
import com.web.flowershopping.Entity.Result;

@Service
public interface OrderService {
    public Result selectAllOrder(String searchString,Integer status_id,boolean is_today_order);

    public Result selectOrderByUserId(Integer user_id, Integer status_id, String order_no);

    public Result deleteOrderByOrderNo(String order_no);

    public Result changeOrderStatus(Integer status_id,Integer order_id);

    public Result changeOrderStatusByOrderNo(Integer status_id,String order_no);

    public Result changeOrderPayStatus(String order_no, Integer status_id);

    public Result createOrder(List<OrderItem> product_info_array, Integer delivery_type_id, Integer delivery_address_id,LocalDateTime delivery_date,Integer user_id, Integer total_amount, String requestNo);

    // 支付
    public Result pay(String order_no, Integer total_amount, String openId);

    // 通过高德地图获取配送距离和时间
    public Result getDeliveryInfoByGaoDe(Integer delivery_address_id);
}
