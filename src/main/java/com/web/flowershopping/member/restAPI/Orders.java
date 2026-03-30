package com.web.flowershopping.member.restAPI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Card;
import com.web.flowershopping.Entity.OrderItem;
import com.web.flowershopping.Entity.Product;
import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Entity.User;
import com.web.flowershopping.Service.OrderService;
import com.web.flowershopping.common.sessions;
import com.web.flowershopping.common.Exception.ParamException;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RestController
public class Orders {
    @Resource
    OrderService orderService;
    @PostMapping("/member/orders")
    public Result createOrder(HttpServletRequest request, @RequestBody Map<String, Object> Requestdata) {
        //TODO: process POST request
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        List<Map<String, Object>> rawList =(List<Map<String, Object>>) Requestdata.get("product_info_array");

        List<OrderItem> orderItems = new ArrayList<>();
        for(Map<String, Object> item : rawList){
            OrderItem orderItemRequest = new OrderItem();
            Product product = new Product();
            Card card = new Card();
            product.setProductId((Integer)item.get("productId"));
            card.setCard_id((Integer) item.get("cardId"));
            orderItemRequest.setProduct(product);
            orderItemRequest.setCard(card);
            orderItemRequest.setIs_anonymous((Integer) item.get("is_anonymous"));
            orderItemRequest.setQuantity((Integer) item.get("quantity"));
            orderItemRequest.setComment((String) item.get("comment"));
            orderItems.add(orderItemRequest);
        }
        Integer user_id = (Integer) Requestdata.get("user_id");
        Integer total_amount = (Integer) Requestdata.get("total_amount");
        Integer delivery_type_id = (Integer) Requestdata.get("delivery_type_id");
        Integer delivery_address_id = (Integer) Requestdata.get("delivery_address_id");
        LocalDateTime delivery_date_str = LocalDateTime.parse(Requestdata.get("delivery_date").toString(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String requestNo = (String)Requestdata.get("requestNo");
        Result result = orderService.createOrder(orderItems, delivery_type_id, delivery_address_id, delivery_date_str, user_id, total_amount, requestNo);
        return result;
    }

    // 微信支付
    @PostMapping("/member/orders/wechat-pay")
    public Result wechatPay(HttpServletRequest request, @RequestBody Map<String, Object> Requestdata) {
        //TODO: process POST request
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        String order_no = (String) Requestdata.get("order_no");
        Integer total_amount = (Integer) Requestdata.get("total_amount");
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user_id");
        System.out.println((User) session.getAttribute("user_id"));
        if(user == null){
            throw new ParamException("用户未登录");
        }
        String openId = user.getOpenid();
        if(openId == null || openId.isEmpty()){
            throw new ParamException("用户未绑定微信，无法使用微信支付");
        }
        // 微信支付逻辑
        Result result = orderService.pay(order_no, total_amount, openId);
        return result;
    }

    // 生成requestNo
    @PostMapping("/member/orders/requestNo")
    public Result generateRequestNo(HttpServletRequest request) {
        //TODO: process POST request
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        String requestNo = UUID.randomUUID().toString();
        Result result = new Result();
        result.setStatus(200);
        result.setData(requestNo);
        return result;
    }
    
}
