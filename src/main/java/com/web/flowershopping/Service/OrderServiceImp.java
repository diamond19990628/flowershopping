package com.web.flowershopping.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.web.flowershopping.Entity.DeliveryAddress;
import com.web.flowershopping.Entity.Order;
import com.web.flowershopping.Entity.OrderItem;
import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Entity.Status;
import com.web.flowershopping.Entity.User;
import com.web.flowershopping.Entity.deliveryType;
import com.web.flowershopping.Mapper.OrderMapper;
import com.web.flowershopping.common.getImagePath;

import jakarta.annotation.Resource;

@Service
public class OrderServiceImp implements OrderService{
    @Resource
    OrderMapper orderMapper;
    @Resource
    getImagePath getImagePath;

    private static final ConcurrentHashMap sessionStore = new ConcurrentHashMap<>();

    Integer order_id = 0;

    @Override
    public Result selectAllOrder(String searchString,Integer status_id,boolean is_today_order) {
        String order_no_res = "^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])([01]\\d|2[0-3])[0-5]\\d[0-5]\\d$";
        User user = new User();
        Order order =  new Order();
        if(searchString != null){
            // 通过正则表达式确认输入内容
            if(searchString.matches("^1[3-9]\\d{9}$")){
                user.setTel(searchString);
            }else if(searchString.matches(order_no_res)){
                order.setOrder_no(searchString);
            }else{
                user.setNickName(searchString);
            }
        }
        Status status = new Status();
        status.setStatusId(status_id);
        List<Order> orderInfoResult = orderMapper.selectAllOrder(user, order, status,is_today_order);
        for(int i = 0;i<orderInfoResult.size();i++){
            Order currentOrder = orderInfoResult.get(i);
            for(int j = 0;j<currentOrder.getOrder_items().size();j++){
                OrderItem currentOrderItem = currentOrder.getOrder_items().get(j);
                // 图片置换
                String imagePath = getImagePath.changeImagePath(currentOrderItem.getAttachedFilePath());
                currentOrderItem.setAttachedFilePath(imagePath);
            }
        }
        Result result = new Result();
        result.setData(orderInfoResult);
        result.setStatus(200);
        return result;
    }

    @Override
    @Transactional
    public Result changeOrderStatus(Integer status_id, Integer order_id) {
        Result result = new Result();
        // 普通发货
        if(status_id==2){
            orderMapper.changeOrderStatus(status_id, order_id);
            result.setStatus(200);
        }else if(status_id==3){
            // 处理售后（不知道具体式样，暂时不实装）
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status_id参数非法");
        }
        return result;
    }

    // 创建订单，涉及到订单项的创建和库存的扣除，使用事务管理保证数据一致性
    @Transactional
    @Override
    public Result createOrder(List<OrderItem> product_info_array, Integer delivery_type_id, Integer delivery_address_id,
            LocalDateTime delivery_date, Integer user_id, Integer total_amount) {
        for(OrderItem orderItem : product_info_array){
            if(orderItem.getProduct().getProductId() == null || orderItem.getQuantity() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "订单项参数不完整");
            }
            // 获取当前库存量和版本号
            Map<String, Object> stockInfo = orderMapper.selectStock(orderItem.getProduct().getProductId());
            if(stockInfo == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "商品库存信息不存在");
            }
            if((Integer) stockInfo.get("stock_count") <= 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "商品库存不足");
            }
            // 扣除库存，使用乐观锁版本号控制并发
            int rowsAffected = orderMapper.updateStock(orderItem.getProduct().getProductId(), orderItem.getQuantity(), (Integer) stockInfo.get("version"));
            if(rowsAffected == 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "商品库存更新失败，可能是由于库存不足或版本号过期，请重试");
            }
        }
        // 订单创建逻辑
        //创建订单号
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        String time = now.format(formatter);
        String order_no = time + String.format("%04d", user_id);
        // 创建锁粒度
        Object lock = sessionStore.computeIfAbsent(order_no+"_"+user_id, k -> new Object());
        synchronized(lock){
            Order order = new Order();
            User user = new User();
            deliveryType deliveryType = new deliveryType();
            DeliveryAddress deliveryAddress = new DeliveryAddress();
            deliveryType.setDelivery_type_id(delivery_type_id);
            user.setUser_id(user_id);
            order.setOrder_no(order_no);
            order.setUser(user);
            order.setTotal_amount(total_amount);
            order.setDeliverytype(deliveryType);
            deliveryAddress.setDelivery_address_id(delivery_address_id);
            order.setDeliveryAddress(deliveryAddress);
            order.setCreate_time(LocalDateTime.now());
            order.setDelivery_date(delivery_date);
            int rowsAffected = orderMapper.createOrder(order);
            if(rowsAffected == 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "订单创建失败");
            }
            // 获取新创建订单的ID
            order_id = order.getOrder_id();
            System.out.println("新创建订单ID: " + order_id);
            // 创建订单项
            for(OrderItem orderItem : product_info_array){
                orderItem.setOrder_id(order_id);
                int itemRowsAffected = orderMapper.createOrderItems(orderItem);
                if(itemRowsAffected == 0){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "订单项创建失败");
                }
            }
        }
        Order selectedOrder = orderMapper.selectOrderById(order_id);
        Result result = new Result();
        result.setStatus(200);
        result.setData(selectedOrder);
        return result;
    }
}
