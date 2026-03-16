package com.web.flowershopping.manager.Entity;

import java.util.List;

public class CategoriesAll {
    private Integer category_id;
    private String category_name;
    private List<Category> parent_category;
    public Integer getCategory_id() {
        return category_id;
    }
    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public List<Category> getParent_category() {
        return parent_category;
    }
    public void setParent_category(List<Category> parent_category) {
        this.parent_category = parent_category;
    }
    @Override
    public String toString() {
        return "CategoriesAll [category_id=" + category_id + ", category_name=" + category_name + ", parent_category="
                + parent_category + "]";
    }
    
}
