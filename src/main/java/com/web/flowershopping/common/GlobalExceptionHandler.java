package com.web.flowershopping.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.common.Exception.CreateException;
import com.web.flowershopping.common.Exception.DeleteException;
import com.web.flowershopping.common.Exception.LoginException;
import com.web.flowershopping.common.Exception.ParamException;
import com.web.flowershopping.common.Exception.ReadException;
import com.web.flowershopping.common.Exception.TokenException;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Result> handleException(LoginException e) {

        Result result = new Result();
        result.setStatus(401);
        result.setMsg(e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<Result> handleException(TokenException e) {

        Result result = new Result();
        result.setStatus(403);
        result.setMsg("服务器异常");

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
    }

    @ExceptionHandler(CreateException.class)
    public ResponseEntity<Result> handleException(CreateException e){
        Result result = new Result();
        result.setStatus(404);
        result.setMsg(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @ExceptionHandler(ReadException.class)
    public ResponseEntity<Result> handleException(ReadException e){
        Result result = new Result();
        result.setStatus(404);
        result.setMsg(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @ExceptionHandler(ParamException.class)
    public ResponseEntity<Result> handleException(ParamException e){
        Result result = new Result();
        result.setStatus(400);
        result.setMsg(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        Result result = new Result();
        result.setStatus(400);
        result.setMsg("参数必须为数字");
        return result;
    }

    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<Result> handleException(DeleteException e){
        Result result = new Result();
        result.setStatus(404);
        result.setMsg(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }
}
