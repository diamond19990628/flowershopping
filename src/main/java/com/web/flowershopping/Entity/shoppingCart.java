package com.web.flowershopping.Entity;

public class shoppingCart {
    private Product product;
    private Card card;
    private Integer is_anonymous;
    private String comment;
    private Integer quantity;
    private String attachedFilePath;
    private Integer user_id;
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
    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    @Override
    public String toString() {
        return "shoppingCart [product=" + product + ", card=" + card + ", is_anonymous=" + is_anonymous + ", comment="
                + comment + ", quantity=" + quantity + ", attachedFilePath=" + attachedFilePath + ", user_id=" + user_id
                + "]";
    }
    
}
