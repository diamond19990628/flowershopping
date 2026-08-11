package com.web.flowershopping.member.restAPI;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.flowershopping.Entity.Card;
import com.web.flowershopping.Entity.Order;
import com.web.flowershopping.Entity.OrderItem;
import com.web.flowershopping.Entity.Product;
import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Entity.User;
import com.web.flowershopping.Mapper.OrderMapper;
import com.web.flowershopping.Mapper.ProductMapper;
import com.web.flowershopping.Service.OrderService;
import com.web.flowershopping.common.WXPayUtility;
import com.web.flowershopping.common.WeChat;
import com.web.flowershopping.common.sessions;
import com.web.flowershopping.common.Exception.ParamException;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import okhttp3.Headers;



@RestController
public class Orders {
    @Value ("${wechat.apiV3Key}")
    private String apiV3Key;
    @Value ("${wechat.pubKeyID}")
    private String wechatpayPublicKey;
    @Value ("${wechat.pubKeyPath}")
    private String wechatpayPublicKeyPath;

    @Resource
    OrderService orderService;
    @Resource
    OrderMapper orderMapper;
    @Resource
    ProductMapper productMapper;
    @Resource
    WeChat wechat;
    @PostMapping("/member/orders")
    public Result createOrder(HttpServletRequest request, @RequestBody Map<String, Object> Requestdata) {
        //TODO: process POST request
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        List<Map<String, Object>> rawList =(List<Map<String, Object>>) Requestdata.get("product_info_array");
        int amount = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for(Map<String, Object> item : rawList){
            OrderItem orderItemRequest = new OrderItem();
            Product product = new Product();
            Card card = new Card();
            product.setProductId((Integer)item.get("productId"));
            // 查询该商品金额
            Integer productAmount = productMapper.selectProductAmountByProductId((Integer)item.get("productId"));
            card.setCard_id((Integer) item.get("cardId"));
            orderItemRequest.setProduct(product);
            orderItemRequest.setCard(card);
            orderItemRequest.setIs_anonymous((Integer) item.get("is_anonymous"));
            orderItemRequest.setQuantity((Integer) item.get("quantity"));
            orderItemRequest.setComment((String) item.get("comment"));
            orderItems.add(orderItemRequest);
            amount += (Integer) item.get("quantity") * productAmount;
        }
        Integer user_id = (Integer) Requestdata.get("user_id");
        Integer total_amount = amount;
        Integer delivery_type_id = (Integer) Requestdata.get("delivery_type_id");
        Integer delivery_address_id = (Integer) Requestdata.get("delivery_address_id");
        LocalDateTime delivery_date_str = LocalDateTime.parse(Requestdata.get("delivery_date").toString(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String requestNo = (String)Requestdata.get("requestNo");
        Result result = orderService.createOrder(orderItems, delivery_type_id, delivery_address_id, delivery_date_str, user_id, total_amount, requestNo);
        return result;
    }

    // 获取订单列表
    @GetMapping("/member/orders/{user_id}")
    public Result getMethodName(HttpServletRequest request, @PathVariable("user_id") Integer user_id,@RequestParam(value = "status_id", defaultValue = "0") Integer status_id,@RequestParam(value = "order_no", defaultValue = "") String order_no) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = orderService.selectOrderByUserId(user_id, status_id, order_no);
        return result;
    }

    // 修改订单状态，主要用于用户取消订单等操作
    @PatchMapping("/member/orders/{order_no}")
    public Result patchOrders(HttpServletRequest request,@PathVariable("order_no")String order_no,@RequestBody Map<String, Object> data){
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Integer status_id = Integer.valueOf(data.get("status_id").toString());
        Result result = orderService.changeOrderStatusByOrderNo(status_id, order_no);
        return result;
    }
    
    // 删除订单
    @DeleteMapping("/member/orders/{order_no}")
    public ResponseEntity deleteOrders(HttpServletRequest request,@PathVariable("order_no")String order_no){
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = orderService.deleteOrderByOrderNo(order_no);
        if(result.getStatus() == 204){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.status(result.getStatus()).build();
        }
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

    // 支付成功后专用的回调接口，供微信支付回调使用，更新订单支付状态
    @PostMapping("/pay/notify")
    public Map<String,String> notify(@RequestBody String body,
                                  HttpServletRequest request) {
        try{
            Headers headers = extractHeaders(request);
            String apiV3Key = (String) this.apiV3Key;
            String wechatpayPublicKey = (String) this.wechatpayPublicKey;
            WXPayUtility wxPayUtility = new WXPayUtility();
            PublicKey wechatpayPublicKeyObj = wxPayUtility.loadPublicKeyFromPath(wechatpayPublicKeyPath);
            WXPayUtility.Notification notification = wxPayUtility.parseNotification(apiV3Key, wechatpayPublicKey, wechatpayPublicKeyObj, headers, body);
            String plaintext = notification.getPlaintext();
            Map<String, Object> data = wxPayUtility.fromJson(plaintext, Map.class);
            String order_no = (String) data.get("out_trade_no");
            String tradeState = (String) data.get("trade_state");
            Map<String, Object> amountMap = (Map<String, Object>) data.get("amount");
            Integer amount = ((Number) amountMap.get("total")).intValue();
            // 真实代码中应该验证订单号和金额等信息，确保回调数据的合法性和安全性
            if("SUCCESS".equals(tradeState)){
                // 验证订单是否存在
                Order order = orderMapper.selectOrderByOrderNo(order_no, null);
                if (order == null) {
                    String refund_no = UUID.randomUUID().toString()+data.get("out_trade_no");
                    wechat.refundWithRequestRefund(order_no, refund_no, amount);
                    return Map.of("code", "SUCCESS", "发起退款", "成功");
                }else{
                    // 更新订单支付状态
                    orderService.changeOrderPayStatus(order_no, 1);
                    return Map.of("code", "SUCCESS", "message", "成功");
                }
            }
        }catch(Exception e){
            // 处理异常情况，记录日志等
            e.printStackTrace();
            return Map.of("code", "FAIL", "message", "失败");
        }
        return Map.of("code", "FAIL", "message", "失败");
    }

    // 测试接口，模拟微信支付回调
    @PostMapping("/test/pay/notify")
    public String postMethodName(@RequestBody Map<String, Object> body) {
        //TODO: process POST request
        String order_no = (String) body.get("order_no_test");
        orderService.changeOrderPayStatus(order_no, 1);
        return "success";
    }

    // 退款接口
    @PostMapping("/pay/refund")
    public Result refund(HttpServletRequest request, @RequestBody Map<String, Object> body) {
        //TODO: process POST request
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        if(!body.containsKey("order_no") || !body.containsKey("refund_amount")){
            throw new ParamException("缺少必要字段");
        }
        String order_no = (String) body.get("order_no");
        Integer refund_amount = (Integer) body.get("refund_amount");
        String refund_no = UUID.randomUUID().toString()+order_no;
        
        wechat.refundWithRequestRefund(order_no, refund_no, refund_amount*100);
        int changeRefundStatusResult = orderMapper.changeRefundStatusByOrderNo(order_no);
        if(changeRefundStatusResult == 0){
            throw new RuntimeException("订单状态更新失败，可能订单不存在");
        }
        Result result = new Result();
        result.setStatus(200);
        result.setMsg("退款请求已发起");
        return result;
    }
    
    // 微信退款回调接口
    @PostMapping("/refund/notify")
    public Map<String, String> postMethodName(@RequestBody String body,
                                  HttpServletRequest request) {
        //TODO: process POST request
        try{
            Headers headers = extractHeaders(request);
            String apiV3Key = (String) this.apiV3Key;
            String wechatpayPublicKey = (String) this.wechatpayPublicKey;
            WXPayUtility wxPayUtility = new WXPayUtility();
            PublicKey wechatpayPublicKeyObj = wxPayUtility.loadPublicKeyFromPath(wechatpayPublicKeyPath);
            WXPayUtility.Notification notification = wxPayUtility.parseNotification(apiV3Key, wechatpayPublicKey, wechatpayPublicKeyObj, headers, body);
            String plaintext = notification.getPlaintext();
            Map<String, Object> data = wxPayUtility.fromJson(plaintext, Map.class);
            String order_no = (String) data.get("out_trade_no");
            String tradeState = (String) data.get("refund_status");
            String outRefundNo = (String) data.get("out_refund_no");
            if("SUCCESS".equals(tradeState)){
                Order order =orderMapper.selectOrderByOrderNo(order_no,null);
                System.out.println(order);
                if(order != null){
                    Integer order_id = order.getOrder_id();
                    // 查询订单项详情
                    List<OrderItem> orderItems = orderMapper.selectOrderItemByOrderId(order_id);
                    // 恢复库存
                    for(OrderItem item : orderItems){
                        Integer product_id = item.getProduct().getProductId();
                        Integer quantity = item.getQuantity();
                        Integer version = (Integer)orderMapper.selectStockById(product_id).get("version");
                        orderMapper.restoreStock(product_id, quantity, version);
                    }
                    // 删除订单项
                    orderMapper.deleteOrderItemsByOrderId(order_id);
                    // 删除该订单
                    orderMapper.deleteOrderByOrderNo(order_no);
                }
                // 将该订单信息存入退款记录表，供管理员查询和统计等使用
                orderMapper.createRefundRecord(outRefundNo, order_no, 1);
                return Map.of("code", "SUCCESS", "message", "成功");
            }
        }catch(Exception e){
            // 处理异常情况，记录日志等
            e.printStackTrace();
            return Map.of("code", "FAIL", "message", "失败");
        }
        return Map.of("code", "FAIL", "message", "失败");
    }
    
    @PostMapping("/test/refund/notify")
    public String test(@RequestBody Map<String, Object> body) {
        String order_no = (String) body.get("order_no");
        orderMapper.deleteOrderByOrderNo(order_no);
        // 将该订单信息存入退款记录表，供管理员查询和统计等使用
        orderMapper.createRefundRecord("testtesttest", order_no, 1);
        return "success";
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

    // 通过高德地图API获取配送距离和预计配送时间
    @GetMapping("/member/shippingfee")
    public Result getMethodName(HttpServletRequest request,@RequestParam("delivery_address_id") Integer delivery_address_id) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = orderService.getDeliveryInfoByGaoDe(delivery_address_id);
        return result;
    }
    
    
    private Headers extractHeaders(HttpServletRequest request) {
        Headers.Builder builder = new Headers.Builder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            builder.add(name, request.getHeader(name));
        }
        return builder.build();
    }
}
