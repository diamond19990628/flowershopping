package com.web.flowershopping.manager.restAPI;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Entity.User;
import com.web.flowershopping.Service.UserLoginService;
import com.web.flowershopping.common.WeChat;
import com.web.flowershopping.common.sessions;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RestController
public class loginController {
    @Resource
    UserLoginService userloginService;
    @Autowired
    WeChat getwechatopenid;
    
    private static final ConcurrentHashMap sessionStore = new ConcurrentHashMap<>();
    @RequestMapping("/login")
    public Result getLoginController(HttpSession session,@RequestParam("code") String code,@RequestParam("nickName") String nickname,@RequestParam("phoneNumber") String phoneNumber) {
        User user = new User();
        user.setOpenid(getwechatopenid.getWechatOpenId(code));
        user.setTel(phoneNumber);
        user.setNickName(nickname);
        String lockName = user.getOpenid();
        Object lock = sessionStore.computeIfAbsent(lockName, k-> new Object());
        synchronized (lock) {
            Result result = userloginService.selectByOpenId(user);
            if(result.getStatus() == 200){
                String token = UUID.randomUUID().toString();
                session.setAttribute("user_id", result.getData());
                session.setAttribute("token", token);
                System.out.println("生成的token: " + token); // 👉 添加日志输出，帮助调试
                result.setToken(token);
            }
            return result;
        }
        
    }

    @PostMapping("/login/phone")
    public Result getPhone(@RequestBody Map<String, Object> Requestdata) {
        //TODO: process POST request
        String code = (String) Requestdata.get("code");
        String lockName = code;
        Object lock = sessionStore.computeIfAbsent(lockName, k-> new Object());
        synchronized (lock) {
            Result result = userloginService.selectByPhoneNumber(code);
            return result;
        }
    }
    
    // 登录状态验证
    @PostMapping("/login/verify")
    public Result verifyLogin(HttpServletRequest request,HttpSession session) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = new Result();
        result.setStatus(200);
        result.setData(session.getAttribute("user_id"));
        return result;
    }
}
