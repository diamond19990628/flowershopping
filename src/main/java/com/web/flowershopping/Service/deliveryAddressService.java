package com.web.flowershopping.Service;

import org.springframework.stereotype.Service;

import com.web.flowershopping.Entity.Result;

@Service
public interface deliveryAddressService {
    public Result getDeliveryAddress(Integer delivery_address_id);

    public Result addDeliveryAddress(Integer user_id, String delivery_address, String receive_name, String recipient_phone);

    public Result updateDeliveryAddress(Integer delivery_address_id, String delivery_address, String receive_name, String recipient_phone);

    public Result deleteDeliveryAddress(Integer delivery_address_id);
}
