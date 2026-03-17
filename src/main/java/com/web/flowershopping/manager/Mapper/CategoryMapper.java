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

    public int createNewCategory(
        Map<String,Object> CategoryParam
    );

    // 根据分类ID查询该分类是否存在产品
    public int selectProductCountWithCategoryID(
        @Param("category_id")Integer category_id
    );

    public int deleteCategoryWithID(
        @Param("category_id")Integer category_id
    );

    public List<Category> selectChildCategoryIdwithCategoryId(
        @Param("category_id")Integer category_id
    );

    public Category selectAllCategoryWithID(
        @Param("category_id")Integer category_id
    );

    public int deleteAllChild(
        @Param("IdList") List<Category> IdList
    );

    public int updateCategory(
        @Param("category_id")Integer category_id,
        @Param("category_name")String category_name
    );

    public CategoriesAll selectAllCategoriesInfoWithID(
        @Param("category_id")Long category_id
    );
}
