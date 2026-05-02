package com.web.flowershopping.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Entity.User;
import com.web.flowershopping.Mapper.UserLoginMapper;
import com.web.flowershopping.common.WeChat;
import com.web.flowershopping.common.Exception.ParamException;

import jakarta.annotation.Resource;

@Service
public class UserLoginServiceImp implements UserLoginService{

    @Resource
    UserLoginMapper userLoginMapper;

    @Autowired
    WeChat getwechatopenid;

    @Override
    public Result selectByOpenId(User user){
        User userInfoResult = userLoginMapper.selectByOpenId(user);
        System.out.println("查询结果: " + userInfoResult);
        Result result = new Result();
        if(userInfoResult != null){
            result.setStatus(200);
            result.setData(userInfoResult);
            result.setMsg("success");
        }else{
            throw new ParamException("用户不存在");
        }
        return result;
    }

    @Override
    public Result selectByPhoneNumber(String code){
        String phoneNumber = getwechatopenid.getPhone(code);
        // 通过手机号查询该用户是否存在
        if(phoneNumber == null){
            throw new HttpClientErrorException(
                HttpStatus.PRECONDITION_FAILED,
                "微信错误"
            );
        }
        // 通过该手机号查询用户信息
        User userInfoResult = userLoginMapper.selectByPhoneNumber(phoneNumber);
        Result result = new Result();
        Map<String, String> entity = new HashMap<>();
        entity.put("phoneNumber", phoneNumber);
        result.setStatus(200);
        if(userInfoResult == null){         
            entity.put("isNewUser", "true");
        }else{
            entity.put("isNewUser", "false");
        }
        result.setData(entity);
        result.setStatus(200);
        result.setMsg("success");
        return result;
    }

    @Override
    public Result registerNewUser(String openid, String nickname, String phoneNumber) {
        // TODO Auto-generated method stub
        User user = new User();
        user.setOpenid(openid);
        user.setNickName(nickname);
        user.setTel(phoneNumber);
        userLoginMapper.insertUser(user);
        Result result = new Result();
        result.setStatus(200);
        result.setData(user);
        return result;
    }
}
