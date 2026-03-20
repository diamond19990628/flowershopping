package com.web.flowershopping.Entity;

public class DeliveryAddress {
    private Integer delivery_address_id;
    private String delivery_address;

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

    @Override
    public String toString() {
        return "DeliveryAddress{" +
                "delivery_address_id=" + delivery_address_id +
                ", delivery_address='" + delivery_address + '\'' +
                '}';
    }
}
