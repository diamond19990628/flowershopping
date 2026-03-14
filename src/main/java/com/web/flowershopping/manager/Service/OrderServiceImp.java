package com.web.flowershopping.manager.Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.web.flowershopping.manager.Entity.Order;
import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Entity.Status;
import com.web.flowershopping.manager.Entity.User;
import com.web.flowershopping.manager.Mapper.OrderMapper;

import jakarta.annotation.Resource;

@Service
public class OrderServiceImp implements OrderService{
    @Resource
    OrderMapper orderMapper;

    @Override
    public Result selectAllOrder(String searchString,Integer status_id) {
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
        List<Map<String,Object>> orderInfoResult = orderMapper.selectAllOrder(user, order, status);
        Result result = new Result();
        result.setData(orderInfoResult);
        result.setStatus(200);
        return result;
    }
}
