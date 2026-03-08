package com.web.flowershopping.manager.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.web.flowershopping.manager.Entity.User;

@Mapper
public interface UserLoginMapper {
    User selectByOpenId(User user);

    void insertUser(User user);
}
