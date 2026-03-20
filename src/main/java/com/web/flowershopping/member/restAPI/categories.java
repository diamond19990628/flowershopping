package com.web.flowershopping.member.restAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Service.CategoryService;

import jakarta.annotation.Resource;


@RestController
public class categories {
    @Resource
    CategoryService categoryService;

    @GetMapping("/member/categories/all")
    public Result getCategories() {
        Result result = categoryService.selectAllParentAndChildCategory();
        return result;
    }
    
}
