package com.web.flowershopping.manager.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.flowershopping.common.Exception.CreateException;
import com.web.flowershopping.manager.Entity.CategoriesAll;
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
}
