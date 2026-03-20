package com.web.flowershopping.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.web.flowershopping.Entity.Order;
import com.web.flowershopping.Entity.OrderItem;
import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Entity.Status;
import com.web.flowershopping.Entity.User;
import com.web.flowershopping.Mapper.OrderMapper;
import com.web.flowershopping.common.getImagePath;

import jakarta.annotation.Resource;

@Service
public class OrderServiceImp implements OrderService{
    @Resource
    OrderMapper orderMapper;
    @Resource
    getImagePath getImagePath;

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
}
