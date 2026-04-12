package com.web.flowershopping.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.Entity.DeliveryAddress;
import com.web.flowershopping.Entity.User;

@Mapper
public interface UserLoginMapper {
    User selectByOpenId(User user);

    User selectByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    void insertUser(User user);

    DeliveryAddress selectDeliveryAddressById(@Param("delivery_address_id") Integer delivery_address_id);

    int createDeliveryAddress(
        @Param("deliveryAddress") DeliveryAddress deliveryAddress
    );

    int updateDeliveryAddress(
        @Param("deliveryAddress") DeliveryAddress deliveryAddress
    );

    void deleteDeliveryAddress(
        @Param("delivery_address_id") Integer delivery_address_id
    );
}
