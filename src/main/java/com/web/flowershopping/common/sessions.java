package com.web.flowershopping.common;

import com.web.flowershopping.common.Exception.LoginException;
import com.web.flowershopping.common.Exception.TokenException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class sessions {
    public static void auth_session(HttpServletRequest request,String token){
        HttpSession session = request.getSession(false);
        System.out.println("session="+session);
        if(session == null){
            throw new LoginException("未登录或session已失效");
        }else if(!session.getAttribute("token").equals(token)){
            throw new TokenException("Token失效");
        }
    }
}
