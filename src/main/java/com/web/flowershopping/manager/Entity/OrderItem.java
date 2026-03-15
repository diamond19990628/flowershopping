package com.web.flowershopping.manager.Entity;

public class OrderItem {
    private Product product;
    private Card card;
    private Integer is_anonymous;
    private String comment;
    private Integer quantity;
    private String attachedFilePath;
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Card getCard() {
        return card;
    }
    public void setCard(Card card) {
        this.card = card;
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
    public String getAttachedFilePath() {
        return attachedFilePath;
    }
    public void setAttachedFilePath(String attachedFilePath) {
        this.attachedFilePath = attachedFilePath;
    }
    @Override
    public String toString() {
        return "OrderItem [product=" + product + ", card=" + card + ", is_anonymous=" + is_anonymous + ", comment="
                + comment + ", quantity=" + quantity + ", attachedFilePath=" + attachedFilePath + ", toString()="
                + super.toString() + "]";
    }
    
}
