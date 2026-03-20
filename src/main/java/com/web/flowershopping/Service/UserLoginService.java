package com.web.flowershopping.Service;

import org.springframework.stereotype.Service;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Entity.User;

@Service
public interface UserLoginService {
    Result selectByOpenId(User user);
}
