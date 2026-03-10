package com.web.flowershopping.manager.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.manager.Entity.Product;

@Mapper
public interface ProductMapper {
    List<Product> selectAllProduct(
        @Param("product") Product product,
        @Param("Low_Stock") boolean Low_Stock
    );
}
