package com.web.flowershopping.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.Entity.Discount;
import com.web.flowershopping.Entity.DiscountType;

@Mapper
public interface DiscountMapper {
    public List<Discount> selectDiscountListWithScope(
        @Param("discount_scope")Integer discount_scope
    );

    public List<DiscountType> selectDiscountTypeList();

    public Discount selectDiscountListWithDiscountId(
        @Param("discount_id")Integer discount_id
    );
    
    public int insertDiscount(Discount discount);

    public int updateDiscount(Discount discount);

    public int deleteDiscount(
        @Param("discount_id")Integer discount_id
    );
}
