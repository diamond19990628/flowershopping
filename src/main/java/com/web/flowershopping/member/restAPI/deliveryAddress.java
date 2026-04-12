package com.web.flowershopping.member.restAPI;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Service.deliveryAddressService;
import com.web.flowershopping.common.sessions;
import com.web.flowershopping.common.Exception.ParamException;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;




@RestController
public class deliveryAddress {

    @Resource
    deliveryAddressService deliveryAddressService;

    @GetMapping("/deliveryAddress/{delivery_address_id}")
    public Result getDeliveryAddress(HttpServletRequest request,@PathVariable("delivery_address_id") Integer delivery_address_id) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = deliveryAddressService.getDeliveryAddress(delivery_address_id);
        return result;
    }
    

    @PostMapping("/member/deliveryAddress/{user_id}")
    public Result createDeliveryAddress(HttpServletRequest request, @PathVariable("user_id") Integer user_id, @RequestBody Map<String, Object> Requestdata) {
        //TODO: process POST request
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        String delivery_address = (String) Requestdata.get("delivery_address");
        String receiver_name = (String) Requestdata.get("receive_name");
        String receiver_phone = (String) Requestdata.get("receive_tel");
        Result result = deliveryAddressService.addDeliveryAddress(user_id, delivery_address, receiver_name, receiver_phone);
        return result;
    }

    @PutMapping("/member/deliveryAddress/{delivery_address_id}")
    public Result putMethodName(HttpServletRequest request, @PathVariable("delivery_address_id") Integer delivery_address_id, @RequestBody Map<String, Object> Requestdata) {
        //TODO: process PUT request
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        String delivery_address = (String) Requestdata.get("delivery_address");
        if(delivery_address == null || delivery_address==""){
            throw new ParamException("delivery_address is not null");
        }
        String receiver_name = (String) Requestdata.get("receive_name");
        if(receiver_name == null || receiver_name==""){
            throw new ParamException("receiver_name is not null");
        }
        String receiver_phone = (String) Requestdata.get("receive_tel");
        if(receiver_phone == null || receiver_phone==""){
            throw new ParamException("receiver_phone is not null");
        }
        Result result = deliveryAddressService.updateDeliveryAddress(delivery_address_id, delivery_address, receiver_name, receiver_phone);
        return result;
    }

    @DeleteMapping("/member/deliveryAddress/{delivery_address_id}")
    public ResponseEntity<Void> deleteDeliveryAddress(HttpServletRequest request, @PathVariable("delivery_address_id") Integer delivery_address_id) {
        // String token = request.getHeader("token");
        // sessions.auth_session(request, token);
        Result result = deliveryAddressService.deleteDeliveryAddress(delivery_address_id);
        if(result.getStatus()==204){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
