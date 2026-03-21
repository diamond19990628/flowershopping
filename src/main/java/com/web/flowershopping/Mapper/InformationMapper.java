package com.web.flowershopping.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.Entity.information;

public interface InformationMapper {
    public List<information> selectAllInformation();

    public int createInformation(
        @Param("information") information information
    );
}
