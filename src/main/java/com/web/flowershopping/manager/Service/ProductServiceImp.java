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

    public Result selectAllProduct(){
        List<Product> productListResult = productmapper.selectAllProduct();
        Result result = new Result();
        result.setData(productListResult);
        result.setStatus(200);
        result.setMsg("success");
        return result;
    }
}
