package com.web.flowershopping.manager.Service;

import org.springframework.stereotype.Service;

import com.web.flowershopping.manager.Entity.Result;

@Service
public interface CategoryService {
    Result selectAllCategory();

    Result selectAllParentAndChildCategory();

    Result createNewCategory(Integer parent_category_id, String category_name);

    Result deleteCategoryWithID(Integer category_id);
}
