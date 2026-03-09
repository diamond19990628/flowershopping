package com.web.flowershopping.manager.Controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.common.getWeChatOpenId;
import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Entity.User;
import com.web.flowershopping.manager.Service.UserLoginService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@RestController
public class loginController {
    @Resource
    UserLoginService userloginService;
    
    @RequestMapping("/login")
    public Result getLoginController(String code,HttpSession session){
        getWeChatOpenId getwechatopenid = new getWeChatOpenId();
        User user = new User();
        user.setOpenid(getwechatopenid.getWechatOpenId(code));
        Result result = userloginService.selectByOpenId(user);
        if(result.getStatus() == 200){
            String token = UUID.randomUUID().toString();
            session.setAttribute("user_id", result.getData());
            session.setAttribute("token", token);
            result.setToken(token);
        }
        return result;
    }
}
