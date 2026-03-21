package com.web.flowershopping.Service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.web.flowershopping.Entity.Result;
@Service
public interface informationService {
    public Result selectAllInformation();

    public Result createInformation(String information_title,String information_content,LocalDateTime publish_start_date,LocalDateTime publish_end_date);

    public Result deleteInformation(Integer information_id);
}
