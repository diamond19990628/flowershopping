package com.web.flowershopping.Service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.web.flowershopping.Entity.Result;

@Service
public interface DiscountService {
    public Result getDiscountList(Integer discount_scope);

    public Result getDiscountType();

    public Result createDiscount(Map<String, Object> param);

    public Result deleteDiscount(Integer discount_id);

    public Result updateDiscount(Integer discount_id);
}
