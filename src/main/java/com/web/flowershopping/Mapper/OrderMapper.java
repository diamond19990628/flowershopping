package com.web.flowershopping.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.Entity.Order;
import com.web.flowershopping.Entity.OrderItem;
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

    public void changeOrderPayStatusByOrderId(
        @Param("order_id") Integer order_id,
        @Param("status_id") Integer status_id
    );

    public Map<String, Object> selectStock(
        @Param("product_id") Integer product_id
    );

    public Map<String, Object> selectStockById(
        @Param("product_id") Integer product_id
    );

    // 更新库存，使用乐观锁版本号控制并发
    public int updateStock(
        @Param("product_id") Integer product_id,
        @Param("quantity") Integer quantity,
        @Param("version") Integer version
    );

    public int restoreStock(
        @Param("product_id") Integer product_id,
        @Param("quantity") Integer quantity,
        @Param("version") Integer version
    );

    public int createOrder(
        @Param("order") Order order,
        @Param("request_no") String requestNo
    );

    public int createOrderItems(
        @Param("orderItems") OrderItem orderItems
    );

    // 通过订单ID查询订单详情
    public Order selectOrderById(
        @Param("order_id") Integer order_id
    );

    // 通过订单ID查询订单项详情
    public List<OrderItem> selectOrderItemByOrderId(
        @Param("order_id") Integer order_id
    );

    // 通过订单编号查询订单详情
    public Order selectOrderByOrderNo(
        @Param("order_no") String order_no,
        @Param("request_no") String request_no
    );

    public int deleteOrderByOrderNo(
        @Param("order_no") String order_no
    );

    public int deleteOrderItemsByOrderId(
        @Param("order_id") Integer order_id
    );
}
