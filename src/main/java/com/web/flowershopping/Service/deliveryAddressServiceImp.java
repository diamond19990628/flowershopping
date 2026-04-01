package com.web.flowershopping.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.flowershopping.Entity.DeliveryAddress;
import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Mapper.UserLoginMapper;

import jakarta.annotation.Resource;
@Service
public class deliveryAddressServiceImp implements deliveryAddressService {
    @Resource
    UserLoginMapper userLoginMapper;

    @Transactional
    @Override
    public Result addDeliveryAddress(Integer user_id, String delivery_address, String receive_name, String recipient_phone) {
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setUser_id(user_id);
        deliveryAddress.setDelivery_address(delivery_address);
        deliveryAddress.setReceive_name(receive_name);
        deliveryAddress.setReceive_tel(recipient_phone);
        userLoginMapper.createDeliveryAddress(deliveryAddress);
        int delivery_address_id = deliveryAddress.getDelivery_address_id();
        DeliveryAddress deliveryAddressresult = new DeliveryAddress();
        deliveryAddressresult.setDelivery_address_id(delivery_address_id);
        deliveryAddressresult.setUser_id(user_id);
        deliveryAddressresult.setDelivery_address(delivery_address);
        deliveryAddressresult.setReceive_name(receive_name);
        deliveryAddressresult.setReceive_tel(recipient_phone);
        Result result = new Result();
        result.setData(deliveryAddressresult);
        result.setStatus(200);
        return result;
    }

    @Transactional
    @Override
    public Result deleteDeliveryAddress(Integer delivery_address_id) {
        userLoginMapper.deleteDeliveryAddress(delivery_address_id);
        Result result = new Result();
        result.setStatus(204);
        return result;
    }
    
}
