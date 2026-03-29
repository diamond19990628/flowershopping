package com.web.flowershopping.Entity;

public class OrderItemRequest {
    private Integer productId;
    private Integer cardId;
    private Integer is_anonymous;
    private String comment;
    private Integer quantity;
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Integer getCardId() {
        return cardId;
    }
    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }
    public Integer getIs_anonymous() {
        return is_anonymous;
    }
    public void setIs_anonymous(Integer is_anonymous) {
        this.is_anonymous = is_anonymous;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "OrderItemRequest [productId=" + productId + ", cardId=" + cardId + ", is_anonymous=" + is_anonymous
                + ", comment=" + comment + ", quantity=" + quantity + "]";
    }
    
}
