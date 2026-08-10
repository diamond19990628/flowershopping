package com.web.flowershopping.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.flowershopping.Entity.Discount;
import com.web.flowershopping.Entity.DiscountType;
import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Mapper.DiscountMapper;
import com.web.flowershopping.common.Exception.ParamException;

import jakarta.annotation.Resource;
@Service
public class DiscountServiceImp implements DiscountService {
    @Resource
    DiscountMapper discountMapper;
    @Override
    public Result getDiscountList(Integer discount_scope) {
        // TODO Auto-generated method stub
        if(discount_scope != 1 && discount_scope != 2) {
            throw new ParamException("discount_scope参数错误");
        }
        List<Discount> discountList = discountMapper.selectDiscountListWithScope(discount_scope);
        Result result = new Result();
        result.setStatus(200);
        result.setData(discountList);
        return result;
    }
    
    @Transactional
    public Result getDiscountType() {
        // TODO Auto-generated method stub
        List<DiscountType> discountTypeList = discountMapper.selectDiscountTypeList();
        Result result = new Result();
        result.setStatus(200);
        result.setData(discountTypeList);
        return result;
    }

    @Override
    @Transactional
    public Result createDiscount(Map<String, Object> param) {
        // TODO Auto-generated method stub
        ArrayList<String> requiredParams = new ArrayList<>();
        requiredParams.add("discount_scope");
        requiredParams.add("discount_title");
        requiredParams.add("start_date");
        requiredParams.add("end_date");
        requiredParams.add("discount_type_id");
        for(String item:requiredParams){
            if(!param.containsKey(item)){
                throw new ParamException(item+"参数缺失");
            }
        }
        // 当discount_type_id为1时，discount_value为折扣率，discount_rate必须存在
        if(param.get("discount_type_id").equals(1) && !param.containsKey("discount_rate")){
            throw new ParamException("discount_rate参数缺失");
        }
        Discount discountDTO = new Discount();
        Integer discount_type_id = (Integer)param.get("discount_type_id");
        if(discount_type_id != 1 && discount_type_id != 2 && discount_type_id != 3){
            throw new ParamException("discount_type_id参数错误");
        }
        discountDTO.setDiscount_scope((Integer)param.get("discount_scope"));

        List<DiscountType> discountTypeList = discountMapper.selectDiscountTypeList();
        for(DiscountType dt: discountTypeList){
            if(dt.getDiscount_type_id().equals(discount_type_id)){
                discountDTO.setDiscountType(dt);
                break;
            }
        }
        String startDate = (String)param.get("start_date");
        String endDate = (String)param.get("end_date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateformatted = LocalDateTime.parse(startDate,formatter);
        LocalDateTime endDateformatted = LocalDateTime.parse(endDate,formatter);
        if(startDate == null || endDate == null){
            throw new ParamException("start_date或end_date参数缺失");
        }
        if(startDateformatted.isAfter(endDateformatted)){
            throw new ParamException("start_date参数必须早于end_date参数");
        }
        discountDTO.setStart_date(startDateformatted);
        discountDTO.setEnd_date(endDateformatted);
        if(param.get("discount_type_id").equals(1)){
            Object discountRate = param.get("discount_rate");
            if(discountRate == null || !(discountRate instanceof Number)){
                throw new ParamException("discount_rate参数类型错误");
            }
            double rate = ((Number) discountRate).doubleValue();
            if(rate < 1 || rate > 10){
                throw new ParamException("discount_rate参数范围必须在1-10之间");
            }
            discountDTO.setDiscount_rate(rate);
        }else if(param.get("discount_type_id").equals(2)){
            Integer threshold_amount = (Integer)param.get("threshold_amount");
            Integer reduction_amount = (Integer)param.get("reduction_amount");
            if(threshold_amount == null || reduction_amount == null){
                throw new ParamException("threshold_amount或reduction_amount参数缺失");
            }
            if(threshold_amount <= 0 || reduction_amount <= 0){
                throw new ParamException("threshold_amount或reduction_amount参数必须大于0");
            }
            discountDTO.setThreshold_amount(threshold_amount);
            discountDTO.setReduction_amount(reduction_amount);
        }else if(param.get("discount_type_id").equals(3)){
            String booking_time_str = (String)param.get("booking_time");
            LocalDateTime booking_time = LocalDateTime.parse(booking_time_str,formatter);
            Integer advance_days = (Integer)param.get("advance_days");
            if(booking_time == null || advance_days == null){
                throw new ParamException("booking_time或advance_days参数缺失");
            }
            if(advance_days <= 0){
                throw new ParamException("advance_days参数必须大于0");
            }
            discountDTO.setBooking_time(booking_time);
            discountDTO.setAdvance_days(advance_days);
        }
        
        discountDTO.setStatus(1);
        discountDTO.setDiscount_title((String)param.get("discount_title"));
        int rowsAffected = discountMapper.insertDiscount(discountDTO);
        if(rowsAffected == 0){
            throw new ParamException("创建折扣信息失败");
        }

        Discount discountResult = discountMapper.selectDiscountListWithDiscountId(discountDTO.getDiscount_id());
        Result result = new Result();
        result.setStatus(200);
        result.setData(discountResult);
        return result;
    }
    
    @Override
    @Transactional
    public Result deleteDiscount(Integer discount_id) {
        int rowsAffected = discountMapper.deleteDiscount(discount_id);
        if(rowsAffected == 0){
            throw new ParamException("删除折扣信息失败");
        }
        Result result = new Result();
        result.setStatus(200);
        result.setMsg("删除成功");
        return result;
    }

    @Override
    @Transactional
    public Result updateDiscount(Integer discount_id) {
        // TODO Auto-generated method stub
        // 根据id获取折扣信息
        Discount discount = discountMapper.selectDiscountListWithDiscountId(discount_id);
        if(discount == null){
            throw new ParamException("折扣信息不存在");
        }
        int status = discount.getStatus();
        // 更新折扣信息
        Discount updatedDiscount = new Discount();
        if(status == 1){
            updatedDiscount.setStatus(0);
        } else {
            updatedDiscount.setStatus(1);
        }
        updatedDiscount.setDiscount_id(discount_id);
        int rowsAffected = discountMapper.updateDiscount(updatedDiscount);
        if(rowsAffected == 0){
            throw new ParamException("更新折扣信息失败");
        }
        Result result = new Result();
        result.setStatus(200);
        result.setMsg("更新成功");
        return result;
    }
}
