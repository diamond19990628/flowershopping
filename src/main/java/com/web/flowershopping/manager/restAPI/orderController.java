package com.web.flowershopping.manager.restAPI;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Service.OrderService;
import com.web.flowershopping.common.sessions;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;


@RestController
public class orderController {
    @Resource
    OrderService orderService;

    @GetMapping("/orders")
    public Result getOrders(HttpServletRequest request,@RequestParam(value = "searchString",required = false)String searchString,@RequestParam(value = "status_id",defaultValue = "0")Integer status_id,@RequestParam(value = "is_today_order",defaultValue = "false")boolean is_today_order) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = orderService.selectAllOrder(searchString, status_id,is_today_order);
        return result;
    }

    @PatchMapping("/orders/{order_id}")
    public Result patchOrders(HttpServletRequest request,@PathVariable("order_id")Integer order_id,@RequestBody Map<String, Object> data){
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Integer status_id = Integer.valueOf(data.get("status_id").toString());
        Result result = orderService.changeOrderStatus(status_id, order_id);
        return result;
    }
}
