package com.web.flowershopping.Service;

import org.springframework.stereotype.Service;

import com.web.flowershopping.Entity.Result;

@Service
public interface shoppingCartService {
    Result selectCartItemsByUserId(int userId);

    Result addCartItem(int product_id, int card_id, int user_id, int quantity,String comment,Integer is_anonymous);

    Result deleteCartItem(int user_id, int product_id);
}
