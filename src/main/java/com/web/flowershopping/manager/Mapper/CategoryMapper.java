package com.web.flowershopping.manager.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.manager.Entity.CategoriesAll;
import com.web.flowershopping.manager.Entity.Category;

@Mapper
public interface CategoryMapper {
    public List<Map<String,Object>> selectAllCategories();

    public List<CategoriesAll> selectAllParentChildCategories();

    public Category selectCategoryWithID(
        @Param("category_id")Integer category_id
    );
}
