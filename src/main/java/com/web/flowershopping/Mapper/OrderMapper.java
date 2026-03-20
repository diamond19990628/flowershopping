package com.web.flowershopping.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.Entity.Order;
import com.web.flowershopping.Entity.Status;
import com.web.flowershopping.Entity.User;

@Mapper
public interface OrderMapper {
    public List<Order> selectAllOrder(
        @Param("User") User user,
        @Param("Order") Order order,
        @Param("Status") Status status,
        @Param("is_today_order") boolean isTodayOrder
    );

    public void changeOrderStatus(
        @Param("status_id") Integer status_id,
        @Param("order_id") Integer order_id
    );
}
