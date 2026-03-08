package com.web.flowershopping.manager.Service;

import org.springframework.stereotype.Service;

import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Entity.User;

@Service
public interface UserLoginService {
    Result selectByOpenId(User user);
}
