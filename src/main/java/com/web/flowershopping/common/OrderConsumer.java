package com.web.flowershopping.common;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.web.flowershopping.Entity.Order;
import com.web.flowershopping.Entity.OrderItem;
import com.web.flowershopping.Mapper.OrderMapper;

import jakarta.annotation.Resource;

@Component
public class OrderConsumer {
    @Resource
    OrderMapper orderMapper;

    @Transactional
    @RabbitListener(queues = "order.check.queue")
    public void receiveMessage(String orderNo) {
        System.out.println("Received message: " + orderNo);
        // 这里可以添加处理订单的逻辑，例如更新订单状态、发送通知等
        Order order =orderMapper.selectOrderByOrderNo(orderNo,null);
        if(order != null){
            Integer order_id = order.getOrder_id();
            if(order.getStatus().getStatusId() == 0){ // 如果订单未支付
                // 查询订单项详情
                List<OrderItem> orderItems = orderMapper.selectOrderItemByOrderId(order_id);
                System.out.println(orderItems);
                // 恢复库存
                for(OrderItem item : orderItems){
                    Integer product_id = item.getProduct().getProductId();
                    Integer quantity = item.getQuantity();
                    Integer version = (Integer)orderMapper.selectStockById(product_id).get("version");
                    orderMapper.restoreStock(product_id, quantity, version);
                }
                // 删除订单项
                orderMapper.deleteOrderItemsByOrderId(order_id);
                // 删除该订单
                orderMapper.deleteOrderByOrderNo(orderNo);
            }
        }
    }
}
