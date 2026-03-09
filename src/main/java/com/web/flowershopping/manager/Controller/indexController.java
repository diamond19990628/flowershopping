package com.web.flowershopping.manager.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.common.sessions;
import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Service.ProductService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;


@RestController
public class indexController {
    @Resource
    ProductService productservice;

    @GetMapping("/product")    
    public Result productGateway(HttpServletRequest request) throws Exception{
        String token = request.getHeader("token");
        sessions.auth_session(request,token);
        Result result = productservice.selectAllProduct();
        return result;
    }
}
