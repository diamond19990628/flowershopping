package com.web.flowershopping.member.restAPI;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Service.shoppingCartService;
import com.web.flowershopping.common.sessions;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;



@RestController
public class shoppingCart {
    @Resource
    shoppingCartService shoppingCartService;
    @GetMapping("/member/shoppingCart/{user_id}")
    public Result getMethodName(HttpServletRequest request,@PathVariable("user_id") Integer user_id) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = shoppingCartService.selectCartItemsByUserId(user_id);
        return result;
    }
    
    @PostMapping("/member/shoppingCart")
    public Result createNewShoppingCart(HttpServletRequest request,@RequestBody Map<String, Object> Requestdata) {
        //TODO: process POST request
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Integer is_anonymous = (Integer) Requestdata.get("is_anonymous");
        Result result = shoppingCartService.addCartItem(Integer.valueOf(Requestdata.get("product_id").toString()), Integer.valueOf(Requestdata.get("card_id").toString()), Integer.valueOf(Requestdata.get("user_id").toString()), Integer.valueOf(Requestdata.get("quantity").toString()), (String) Requestdata.get("comment"), is_anonymous);
        return result;
    }

    @DeleteMapping("/member/shoppingCart/{user_id}/{product_id}")
    public ResponseEntity<Void> deleteCartItem(HttpServletRequest request,@PathVariable Integer user_id, @PathVariable("product_id") Integer product_id) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = shoppingCartService.deleteCartItem(user_id, product_id);
        if(result.getStatus() == 204){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(500).build();
    }
    
}
