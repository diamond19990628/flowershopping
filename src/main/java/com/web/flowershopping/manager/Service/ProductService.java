package com.web.flowershopping.manager.Service;

import org.springframework.stereotype.Service;

import com.web.flowershopping.manager.Entity.Result;

@Service
public interface ProductService {
    Result selectAllProduct(String product_name,Integer status,boolean Low_Stock);
}
