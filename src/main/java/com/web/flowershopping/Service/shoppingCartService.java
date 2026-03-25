package com.web.flowershopping.Service;

import org.springframework.stereotype.Service;

import com.web.flowershopping.Entity.Result;

@Service
public interface shoppingCartService {
    Result selectCartItemsByUserId(int userId);
}
