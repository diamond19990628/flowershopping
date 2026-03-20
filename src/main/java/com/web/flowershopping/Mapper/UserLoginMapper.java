package com.web.flowershopping.Mapper;

import org.apache.ibatis.annotations.Mapper;

import com.web.flowershopping.Entity.User;

@Mapper
public interface UserLoginMapper {
    User selectByOpenId(User user);

    void insertUser(User user);
}
