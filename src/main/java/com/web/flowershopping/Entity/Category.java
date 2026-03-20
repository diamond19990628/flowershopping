package com.web.flowershopping.Entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {
    private int category_id;
    private String category_name;
    private LocalDateTime entryDate;
    private LocalDateTime updateDate;
    public void setCategoryId(int category_id){
        this.category_id = category_id;
    }
    public int getCategoryId(){
        return category_id;
    }
    public void setCategoryName(String category_name){
        this.category_name = category_name;
    }
    public String getCategoryName(){
        return category_name;
    }
    public void setEntryDate(LocalDateTime entryDate){
        this.entryDate = entryDate;
    }
    public LocalDateTime getEntryDate(){
        return entryDate;
    }
    public void setUpdateDate(LocalDateTime updateDate){
        this.updateDate = updateDate;
    }
    public LocalDateTime getUpdateDate(){
        return updateDate;
    }
}
