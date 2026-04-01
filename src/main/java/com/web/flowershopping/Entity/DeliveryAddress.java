package com.web.flowershopping.Entity;

public class DeliveryAddress {
    private Integer delivery_address_id;
    private String delivery_address;
    private Integer user_id;
    private String receive_name;
    private String receive_tel;
    public Integer getDelivery_address_id() {
        return delivery_address_id;
    }
    public void setDelivery_address_id(Integer delivery_address_id) {
        this.delivery_address_id = delivery_address_id;
    }
    public String getDelivery_address() {
        return delivery_address;
    }
    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }
    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    public String getReceive_name() {
        return receive_name;
    }
    public void setReceive_name(String receive_name) {
        this.receive_name = receive_name;
    }
    public String getReceive_tel() {
        return receive_tel;
    }
    public void setReceive_tel(String receive_tel) {
        this.receive_tel = receive_tel;
    }
    @Override
    public String toString() {
        return "DeliveryAddress [delivery_address_id=" + delivery_address_id + ", delivery_address=" + delivery_address
                + ", user_id=" + user_id + ", receive_name=" + receive_name + ", receive_tel=" + receive_tel + "]";
    }
    
}
