package com.web.flowershopping.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.Entity.shoppingCart;

@Mapper
public interface ShoppingCartMapper {
    public List<shoppingCart> selectCartItemsByUserId(@Param("userId") int userId);
}
