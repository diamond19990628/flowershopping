package com.web.flowershopping.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.flowershopping.Entity.DeliveryAddress;
import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Mapper.UserLoginMapper;
import com.web.flowershopping.common.Exception.ParamException;
import com.web.flowershopping.common.Exception.ReadException;

import jakarta.annotation.Resource;
@Service
public class deliveryAddressServiceImp implements deliveryAddressService {
    @Resource
    UserLoginMapper userLoginMapper;

    @Override
    public Result getDeliveryAddress(Integer delivery_address_id) {
        // TODO Auto-generated method stub
        Result result = new Result();
        DeliveryAddress deliveryAddress = userLoginMapper.selectDeliveryAddressById(delivery_address_id);
        if(deliveryAddress == null){
            throw new ReadException("Delivery address not found for ID: " + delivery_address_id);
        }
        result.setStatus(200);
        result.setData(deliveryAddress);
        return result;
    }

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

    @Transactional
    @Override
    public Result updateDeliveryAddress(Integer delivery_address_id, String delivery_address, String receive_name,
            String recipient_phone) {
        // 查询该地址是否存在
        DeliveryAddress existingAddress = userLoginMapper.selectDeliveryAddressById(delivery_address_id);
        if (existingAddress == null) {
            throw new ReadException("Delivery address not found for ID: " + delivery_address_id);
        }
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        deliveryAddress.setDelivery_address_id(delivery_address_id);
        deliveryAddress.setDelivery_address(delivery_address);
        deliveryAddress.setReceive_name(receive_name);
        deliveryAddress.setReceive_tel(recipient_phone);
        int rowsAffected = userLoginMapper.updateDeliveryAddress(deliveryAddress);
        if (rowsAffected > 0) {
            DeliveryAddress deliveryAddressresult = new DeliveryAddress();
            deliveryAddressresult.setDelivery_address_id(delivery_address_id);
            deliveryAddressresult.setDelivery_address(delivery_address);
            deliveryAddressresult.setReceive_name(receive_name);
            deliveryAddressresult.setReceive_tel(recipient_phone);
            Result result = new Result();
            result.setStatus(200);
            result.setData(deliveryAddressresult);
            return result;
        } else {
            throw new ParamException(" Failed to update delivery address. Please check the delivery_address_id and try again.");
        }
    }
    
}
