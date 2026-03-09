package com.web.flowershopping.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.web.flowershopping.common.Exception.LoginException;
import com.web.flowershopping.manager.Entity.Result;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginException.class)
    public Result handleException(LoginException e) {

        Result result = new Result();
        result.setStatus(401);
        result.setMsg(e.getMessage());

        return result;
    }
}
