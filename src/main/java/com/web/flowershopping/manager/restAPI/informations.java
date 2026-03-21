package com.web.flowershopping.manager.restAPI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Service.informationService;
import com.web.flowershopping.common.sessions;
import com.web.flowershopping.common.Exception.ParamException;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;


@RestController
public class informations {
    @Resource
    informationService informationservice;

    @GetMapping("/informations")
    public Result getInformation(HttpServletRequest request) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = informationservice.selectAllInformation();
        return result;
    }

    // 创建新公告
    @PostMapping("/informations")
    public Result createInformation(HttpServletRequest request,@RequestBody Map<String, Object> Requestdata) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        String informationTitle = (String) Requestdata.get("information_title");
        String informationContent = (String) Requestdata.get("information_content");
        LocalDateTime publishStartDate = Requestdata.get("publish_start_date") != null ? LocalDate.parse((String) Requestdata.get("publish_start_date")).atStartOfDay():null;
        LocalDateTime publishEndDate = Requestdata.get("publish_end_date") != null ? LocalDate.parse((String) Requestdata.get("publish_end_date")).atTime(23, 59, 59):null;
        // 判断任意一个字段是否缺失
        if(informationTitle == null || informationContent == null || publishStartDate == null || publishEndDate == null){
            throw new ParamException("缺少必要字段");
        }
        if(publishStartDate.isAfter(publishEndDate)){
            throw new ParamException("发布日期必须早于结束日期");
        }
        Result result = informationservice.createInformation(informationTitle, informationContent, publishStartDate, publishEndDate);
        return result;
    }
    
    // 删除公告
    @DeleteMapping("/informations/{information_id}")
    public ResponseEntity<Void> deleteInformation(HttpServletRequest request,@PathVariable("information_id")Integer information_id){
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = informationservice.deleteInformation(information_id);
        if(result.getStatus() == 204){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.status(result.getStatus()).build();
        }
    }
    
}
