package com.web.flowershopping.manager.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.flowershopping.common.Exception.CreateException;
import com.web.flowershopping.common.Exception.ParamException;
import com.web.flowershopping.manager.Entity.CategoriesAll;
import com.web.flowershopping.manager.Entity.Category;
import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Mapper.CategoryMapper;

import jakarta.annotation.Resource;
@Service
public class CategoryServiceImp implements CategoryService{

    @Resource
    CategoryMapper categoryMapper;
    public Result selectAllCategory(){

        List<Map<String,Object>> cateGoryList = categoryMapper.selectAllCategories();
        Result result = new Result();
        result.setData(cateGoryList);
        result.setStatus(200);
        return result;
    }
    @Override
    public Result selectAllParentAndChildCategory() {
        List<CategoriesAll> cateGoriesList = categoryMapper.selectAllParentChildCategories();
        Result result = new Result();
        result.setData(cateGoriesList);
        result.setStatus(200);
        return result;
    }
    // 新建分类
    @Override
    @Transactional
    public Result createNewCategory(Integer parent_category_id, String category_name) {
        // TODO Auto-generated method stub
        Map<String,Object> categoryDTO = new HashMap<String,Object>();
        categoryDTO.put("category_name", category_name);
        categoryDTO.put("parent_category_id", parent_category_id);
        int categoryResult = categoryMapper.createNewCategory(categoryDTO);
        
        Result result = new Result();
        if(categoryResult==1){
            Long category_id = Long.valueOf(categoryDTO.get("category_id").toString());
            // 情報調査
            CategoriesAll categoryNewInfo = categoryMapper.selectAllCategoriesInfoWithID(category_id);
            result.setStatus(200);
            result.setData(categoryNewInfo);
        }else{
            throw new CreateException("创建失败");
        }
        return result;
        
    }
    @Override
    @Transactional
    public Result deleteCategoryWithID(Integer category_id) {
        // 查询该分类是否存在产品
        int productCount = categoryMapper.selectProductCountWithCategoryID(category_id);
        if(productCount > 0){
            throw new ParamException("该分类存在产品，无法删除");
        }
        // 查询该分类是是否存在子分类
        List<Category> categoryIdList = categoryMapper.selectChildCategoryIdwithCategoryId(category_id);
        if(categoryIdList.size()>0){
            // 删除旗下所有子分类
            categoryMapper.deleteAllChild(categoryIdList);
        }
        categoryMapper.deleteCategoryWithID(category_id);
        Result result = new Result();
        result.setStatus(204);
        return result;
    }
    @Override
    @Transactional
    public Result updateCategoryWithID(Integer category, String category_name) {
        // 查询该分类是否存在
        Category categoryResult = categoryMapper.selectAllCategoryWithID(category);
        Result result = new Result();
        if(categoryResult != null){
            int updateCategoryResult = categoryMapper.updateCategory(category, category_name);
            // 情報調査
            if(updateCategoryResult>0){
                long category_id = (long)category;
                CategoriesAll categoryNewInfo = categoryMapper.selectAllCategoriesInfoWithID(category_id);
                result.setStatus(201);
                result.setData(categoryNewInfo);
            }else{
                throw new ParamException("出现错误");
            }
        }else{
            throw new CreateException("该分类已经不存在，无法修改");
        }
        return result;
    }
}
