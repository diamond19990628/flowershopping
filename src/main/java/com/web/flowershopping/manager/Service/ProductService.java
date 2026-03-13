package com.web.flowershopping.manager.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.flowershopping.manager.Entity.Result;

@Service
public interface ProductService {
    Result selectAllProduct(String product_name,Integer status,boolean Low_Stock);

    Result selectProductWithID(Integer product_id);

    Result createProduct(String product_name,Integer amount,Integer stock,Integer category,MultipartFile file);
}
