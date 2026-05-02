package com.web.flowershopping.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.Entity.DeliveryAddress;
import com.web.flowershopping.Entity.shoppingCart;

@Mapper
public interface ShoppingCartMapper {
    public List<shoppingCart> selectCartItemsByUserId(@Param("userId") int userId);

    public int selectCartItemCount(@Param("product_id") int product_id, @Param("user_id") int user_id);

    public void updateCartItemQuantity(
        @Param("product_id") int product_id,
        @Param("user_id") int user_id
    );

    public int addCartItem(
        @Param("cartItem") shoppingCart cartItem
    );

    public int deleteCartItem(
        @Param("user_id") int user_id,
        @Param("product_id") int product_id
    );

    public int selectProductCountInCartByProductId(
        @Param("product_id") int product_id
    );

    public List<DeliveryAddress> selectDeliveryAddressByUserId(@Param("user_id") Integer user_id);
}
