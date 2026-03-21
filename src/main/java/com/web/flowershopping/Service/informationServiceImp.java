package com.web.flowershopping.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Entity.information;
import com.web.flowershopping.Mapper.InformationMapper;

import jakarta.annotation.Resource;
@Service
public class informationServiceImp implements informationService {
    @Resource
    InformationMapper informationMapper;
    @Override
    public Result selectAllInformation() {
        List<information> informations = informationMapper.selectAllInformation();
        Result result = new Result();
        if(informations != null){
            result.setStatus(200);
            result.setData(informations);
            result.setMsg("success");
        }
        return result;
    }
    
}
