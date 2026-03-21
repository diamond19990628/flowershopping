package com.web.flowershopping.manager.restAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Service.informationService;
import com.web.flowershopping.common.sessions;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;


@RestController
public class informations {
    @Resource
    informationService informationservice;

    @GetMapping("/informations")
    public Result getMethodName(HttpServletRequest request) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = informationservice.selectAllInformation();
        return result;
    }
    
}
