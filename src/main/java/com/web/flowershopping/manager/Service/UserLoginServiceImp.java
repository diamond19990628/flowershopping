package com.web.flowershopping.manager.Service;

import org.springframework.stereotype.Service;

import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Entity.User;
import com.web.flowershopping.manager.Mapper.UserLoginMapper;

import jakarta.annotation.Resource;

@Service
public class UserLoginServiceImp implements UserLoginService{

    @Resource
    UserLoginMapper userLoginMapper;

    @Override
    public Result selectByOpenId(User user){
        User userInfoResult = userLoginMapper.selectByOpenId(user);
        Result result = new Result();
        if(userInfoResult != null){
            result.setStatus(200);
            result.setData(userInfoResult);
            result.setMsg("success");
        }else{
            result.setStatus(404);
            result.setMsg("User not found");
        }
        return result;
    }
}
