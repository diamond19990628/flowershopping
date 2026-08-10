package com.web.flowershopping.manager.restAPI;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Service.DiscountService;
import com.web.flowershopping.common.sessions;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;




@RestController
public class discountController {
    @Resource
    DiscountService discountService;
    @GetMapping("/discount")
    public Result getMethodName(HttpServletRequest request, @RequestParam Map<String, String> param) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = discountService.getDiscountList(Integer.valueOf(param.get("discount_scope")));
        return result;
    }

    @GetMapping("/discount/type")
    public Result getDiscountType(HttpServletRequest request, @RequestParam Map<String, String> param) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = discountService.getDiscountType();
        return result;
    }

    // POST /discount创建新的折扣活动
    @PostMapping("/discount")
    public Result postMethodName(HttpServletRequest request, @RequestBody Map<String, Object> param) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = discountService.createDiscount(param);
        return result;
    }

    // DELETE /discount/{discount_id} 删除折扣活动
    @DeleteMapping("/discount/{discount_id}")
    public Result deleteMethodName(HttpServletRequest request, @PathVariable("discount_id") Integer discount_id) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = discountService.deleteDiscount(discount_id);
        return result;
    }
    
    // PUT /discount/{discount_id} 更新折扣活动
    @PutMapping("/discount/{discount_id}")
    public Result putMethodName(HttpServletRequest request, @PathVariable("discount_id") Integer id) {
        //TODO: process PUT request
        // String token = request.getHeader("token");
        // sessions.auth_session(request, token);
        Result result = discountService.updateDiscount(id);
        return result;
    }
}