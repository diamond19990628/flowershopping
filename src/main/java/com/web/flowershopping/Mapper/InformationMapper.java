package com.web.flowershopping.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.Entity.information;

public interface InformationMapper {
    public List<information> selectAllInformation();

    public int createInformation(
        @Param("information") information information
    );

    public information selectInformationWithID(
        @Param("information_id")Integer information_id
    );

    public int deleteInformationWithID(
        @Param("information_id")Integer information_id
    );
}
