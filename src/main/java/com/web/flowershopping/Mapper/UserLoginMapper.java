package com.web.flowershopping.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.Entity.User;

@Mapper
public interface UserLoginMapper {
    User selectByOpenId(User user);

    User selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    void insertUser(User user);
}
