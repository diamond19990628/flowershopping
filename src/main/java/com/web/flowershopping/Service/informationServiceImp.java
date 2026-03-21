package com.web.flowershopping.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Entity.information;
import com.web.flowershopping.Mapper.InformationMapper;
import com.web.flowershopping.common.Exception.CreateException;

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
    @Transactional
    @Override
    public Result createInformation(String information_title, String information_content, LocalDateTime publish_start_date,
            LocalDateTime publish_end_date) {
        // TODO Auto-generated method stub
        information informationDTO = new information();
        informationDTO.setInformationTitle(information_title);
        informationDTO.setInformationContent(information_content);
        informationDTO.setPublishStartDate(publish_start_date);
        informationDTO.setPublishEndDate(publish_end_date);
        int informationResult = informationMapper.createInformation(informationDTO);
        Integer information_id = informationDTO.getInformationId();
        information informationNewInfo = new information();
        if(informationResult>0){
            informationNewInfo.setInformationId(information_id);
            informationNewInfo.setInformationTitle(information_title);
            informationNewInfo.setInformationContent(information_content);
            informationNewInfo.setPublishStartDate(publish_start_date);
            informationNewInfo.setPublishEndDate(publish_end_date); 
        }else{
            throw new CreateException("创建失败");
        }
        Result result = new Result();
        result.setStatus(200);
        result.setData(informationNewInfo);
        return result;
    }

}
