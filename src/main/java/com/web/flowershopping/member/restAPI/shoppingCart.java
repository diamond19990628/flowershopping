package com.web.flowershopping.member.restAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Service.shoppingCartService;
import com.web.flowershopping.common.sessions;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;


@RestController
public class shoppingCart {
    @Resource
    shoppingCartService shoppingCartService;
    @GetMapping("/member/shoppingCart/{user_id}")
    public Result getMethodName(HttpServletRequest request,@PathVariable("user_id") Integer user_id) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = shoppingCartService.selectCartItemsByUserId(user_id);
        return result;
    }
    
}
