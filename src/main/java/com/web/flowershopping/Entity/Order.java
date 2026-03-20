package com.web.flowershopping.Entity;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int order_id;
    private String order_no;
    private User user;
    private int total_amount;
    private Status status;
    private deliveryType deliverytype;
    private DeliveryAddress deliveryAddress;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private List<OrderItem> order_items;
    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
    public String getOrder_no() {
        return order_no;
    }
    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getTotal_amount() {
        return total_amount;
    }
    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public deliveryType getDeliverytype() {
        return deliverytype;
    }
    public void setDeliverytype(deliveryType deliverytype) {
        this.deliverytype = deliverytype;
    }
    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    public LocalDateTime getCreate_time() {
        return create_time;
    }
    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }
    public LocalDateTime getUpdate_time() {
        return update_time;
    }
    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }
    public List<OrderItem> getOrder_items() {
        return order_items;
    }
    public void setOrder_items(List<OrderItem> order_items) {
        this.order_items = order_items;
    }
    @Override
    public String toString() {
        return "Order [order_id=" + order_id + ", order_no=" + order_no + ", user=" + user + ", total_amount="
                + total_amount + ", status=" + status + ", deliverytype=" + deliverytype + ", deliveryAddress="
                + deliveryAddress + ", create_time=" + create_time + ", update_time=" + update_time + ", order_items="
                + order_items + "]";
    }
    
    
}
