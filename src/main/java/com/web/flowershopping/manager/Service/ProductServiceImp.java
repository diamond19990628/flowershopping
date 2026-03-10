package com.web.flowershopping.manager.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web.flowershopping.manager.Entity.Product;
import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Mapper.ProductMapper;

import jakarta.annotation.Resource;

@Service
public class ProductServiceImp implements ProductService{
    @Resource
    ProductMapper productmapper;

    public Result selectAllProduct(String product_name,Integer status,boolean Low_Stock){
        Product productReadDTO = new Product();
        if(product_name != null){
            productReadDTO.setProductName(product_name);
        }
        productReadDTO.setStatus(status);
        List<Product> productListResult = productmapper.selectAllProduct(productReadDTO,Low_Stock);
        Result result = new Result();
        result.setData(productListResult);
        result.setStatus(200);
        result.setMsg("success");
        return result;
    }
}
