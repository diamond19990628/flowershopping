package com.web.flowershopping.manager.Service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
    };
}
