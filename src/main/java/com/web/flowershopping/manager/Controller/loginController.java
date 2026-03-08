package com.web.flowershopping.manager.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.common.getWeChatOpenId;
import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Entity.User;
import com.web.flowershopping.manager.Service.UserLoginService;

import jakarta.annotation.Resource;

@RestController
public class loginController {
    @Resource
    UserLoginService userloginService;
    
    @RequestMapping("/login")
    public String getLoginController(String code){
        getWeChatOpenId getwechatopenid = new getWeChatOpenId();
        User user = new User();
        user.setOpenid(getwechatopenid.getWechatOpenId(code));
        Result result = userloginService.selectByOpenId(user);
        return result.toString();
    }
}
