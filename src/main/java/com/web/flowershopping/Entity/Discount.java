package com.web.flowershopping.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Discount {
    private Integer discount_id;
    private Integer discount_scope;
    private String discount_title;
    private Integer status;
    private double discount_rate;
    private double threshold_amount;
    private double reduction_amount;
    private LocalDateTime booking_time;
    private Integer advance_days;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private DiscountType discountType;
    public Integer getDiscount_id() {
        return discount_id;
    }
    public void setDiscount_id(Integer discount_id) {
        this.discount_id = discount_id;
    }
    public Integer getDiscount_scope() {
        return discount_scope;
    }
    public void setDiscount_scope(Integer discount_scope) {
        this.discount_scope = discount_scope;
    }
    public String getDiscount_title() {
        return discount_title;
    }
    public void setDiscount_title(String discount_title) {
        this.discount_title = discount_title;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public double getDiscount_rate() {
        return discount_rate;
    }
    public void setDiscount_rate(double discount_rate) {
        this.discount_rate = discount_rate;
    }
    public double getThreshold_amount() {
        return threshold_amount;
    }
    public void setThreshold_amount(double threshold_amount) {
        this.threshold_amount = threshold_amount;
    }
    public double getReduction_amount() {
        return reduction_amount;
    }
    public void setReduction_amount(double reduction_amount) {
        this.reduction_amount = reduction_amount;
    }
    public LocalDateTime getBooking_time() {
        return booking_time;
    }
    public void setBooking_time(LocalDateTime booking_time) {
        this.booking_time = booking_time;
    }
    public Integer getAdvance_days() {
        return advance_days;
    }
    public void setAdvance_days(Integer advance_days) {
        this.advance_days = advance_days;
    }
    public LocalDateTime getStart_date() {
        return start_date;
    }
    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }
    public LocalDateTime getEnd_date() {
        return end_date;
    }
    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }
    public DiscountType getDiscountType() {
        return discountType;
    }
    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }
    @Override
    public String toString() {
        return "Discount [discount_id=" + discount_id + ", discount_scope=" + discount_scope + ", discount_title="
                + discount_title + ", status=" + status + ", discount_rate=" + discount_rate + ", threshold_amount="
                + threshold_amount + ", reduction_amount=" + reduction_amount + ", booking_time=" + booking_time
                + ", advance_days=" + advance_days + ", start_date=" + start_date + ", end_date=" + end_date
                + ", discountType=" + discountType + "]";
    }
    
}
