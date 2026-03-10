package com.web.flowershopping.manager.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {

    private Integer productId;
    private String productName;
    private Integer attachedFileId;
    private Integer amount;
    private LocalDateTime entryDate;
    private LocalDateTime updateDate;
    private Integer stock;
    private Integer status;

    private AttachedFIlePhoto attachedFile;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getAttachedFileId() {
        return attachedFileId;
    }

    public void setAttachedFileId(Integer attachedFileId) {
        this.attachedFileId = attachedFileId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public AttachedFIlePhoto getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(AttachedFIlePhoto attachedFile) {
        this.attachedFile = attachedFile;
    }
    public Integer getStock(){
        return stock;
    }
    public void setStock(Integer stock){
        this.stock = stock;
    }
    public void setStatus(Integer status){
        this.status = status;
    }
    public Integer getStatus(){
        return status;
    }
    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", attachedFileId=" + attachedFileId +
                ", amount=" + amount +
                ", entryDate=" + entryDate +
                ", updateDate=" + updateDate +
                ", attachedFile=" + attachedFile +
                ", stock="+stock+
                '}';
    }
}