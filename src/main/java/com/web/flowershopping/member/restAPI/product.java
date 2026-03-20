package com.web.flowershopping.member.restAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Service.ProductService;
import com.web.flowershopping.common.Exception.ParamException;

import jakarta.annotation.Resource;


@RestController
public class product {
    @Resource
    ProductService productService;

    @GetMapping("/member/product")
    public Result getMethodName(@RequestParam(required = false) Integer category_id) {
        if(category_id == null) {
            throw new ParamException("分类ID不能为空");
        }
        Result result = productService.selectMemberAllProduct(category_id);
        return result;
    }
    
}
