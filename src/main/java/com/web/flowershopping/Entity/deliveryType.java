package com.web.flowershopping.Entity;

public class deliveryType {
    private Integer delivery_type_id;
    private String delivery_type_name;

    public Integer getDelivery_type_id() {
        return delivery_type_id;
    }

    public void setDelivery_type_id(Integer delivery_type_id) {
        this.delivery_type_id = delivery_type_id;
    }

    public String getDelivery_type_name() {
        return delivery_type_name;
    }

    public void setDelivery_type_name(String delivery_type_name) {
        this.delivery_type_name = delivery_type_name;
    }

    @Override
    public String toString() {
        return "delivery_type{" +
                "delivery_type_id=" + delivery_type_id +
                ", delivery_type_name='" + delivery_type_name + '\'' +
                '}';
    }
}
