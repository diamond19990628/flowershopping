package com.web.flowershopping.member.restAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Service.informationService;

import jakarta.annotation.Resource;


@RestController
public class Memberinformations {
    @Resource
    informationService informationservice;

    @GetMapping("/member/informations")
    public Result getInformation() {
        Result result = informationservice.selectMemberInformations();
        return result;
    }
    
}
