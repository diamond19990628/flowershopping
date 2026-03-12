package com.web.flowershopping.manager.Mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {
    public List<Map<String,Object>> selectAllCategories();
}
