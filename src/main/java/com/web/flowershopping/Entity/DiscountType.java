package com.web.flowershopping.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiscountType {
    private Integer discount_type_id;
    private String discount_type_name;
    public Integer getDiscount_type_id() {
        return discount_type_id;
    }
    public void setDiscount_type_id(Integer discount_type_id) {
        this.discount_type_id = discount_type_id;
    }
    public String getDiscount_type_name() {
        return discount_type_name;
    }
    public void setDiscount_type_name(String discount_type_name) {
        this.discount_type_name = discount_type_name;
    }
    @Override
    public String toString() {
        return "DiscountType [discount_type_id=" + discount_type_id + ", discount_type_name=" + discount_type_name
                + "]";
    }
    
}
