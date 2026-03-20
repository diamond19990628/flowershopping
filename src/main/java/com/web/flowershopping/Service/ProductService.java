package com.web.flowershopping.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.flowershopping.Entity.Result;

@Service
public interface ProductService {
    Result selectAllProduct(String product_name,Integer status,boolean Low_Stock);

    Result selectProductWithID(Integer product_id);

    Result createProduct(String product_name,Integer amount,Integer stock,Integer category,MultipartFile file);

    Result updateProduct(Integer product_id,String product_name,Integer amount,Integer stock,Integer category,MultipartFile file);

    Result UnlistProduct(Integer product_id,Integer status);
}
