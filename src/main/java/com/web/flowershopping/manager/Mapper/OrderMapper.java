package com.web.flowershopping.manager.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.manager.Entity.Order;
import com.web.flowershopping.manager.Entity.Status;
import com.web.flowershopping.manager.Entity.User;

@Mapper
public interface OrderMapper {
    public List<Order> selectAllOrder(
        @Param("User") User user,
        @Param("Order") Order order,
        @Param("Status") Status status
    );
}
