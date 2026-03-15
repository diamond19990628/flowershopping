package com.web.flowershopping.manager.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Service.OrderService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;


@RestController
public class orderController {
    @Resource
    OrderService orderService;

    @GetMapping("/orders")
    public Result getMethodName(HttpServletRequest request,@RequestParam(value = "searchString",required = false)String searchString,@RequestParam(value = "status_id",defaultValue = "0")Integer status_id) {
        // String token = request.getHeader("token");
        // sessions.auth_session(request, token);
        Result result = orderService.selectAllOrder(searchString, status_id);
        return result;
    }
    
}
