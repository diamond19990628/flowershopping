package com.web.flowershopping.common;

import com.web.flowershopping.common.Exception.LoginException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class sessions {
    public static void auth_session(HttpServletRequest request,String token) throws Exception{
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("user_id") == null){
            throw new LoginException("未登录或session已失效");
        }
    }
}
