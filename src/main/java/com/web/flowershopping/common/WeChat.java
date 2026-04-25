package com.web.flowershopping.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAPublicKeyConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.AmountReq;
import com.wechat.pay.java.service.refund.model.CreateRequest;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
@Service
public class WeChat {
    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.secret}")
    private String secret;
    @Value("${wechat.MerchantId}")
    private String MerchantId;
    @Value("${wechat.privateKeyPath}")
    private String privateKeyPath;
    @Value("${wechat.merchantSerialNumber}")
    private String merchantSerialNumber;
    @Value("${wechat.apiV3Key}")
    private String apiV3Key;
    @Value("${wechat.pubKeyPath}")
    private String publicKeyPath;
    @Value("${wechat.pubKeyID}")
    private String publicKeyId;

    //accessToken和expireTime可以用来缓存access_token，避免频繁请求微信服务器
    private String accessToken;
    private long expireTime;

    private final WebClient webClient = WebClient.builder().build();

    public String getWechatOpenId(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session" +
        "?appid=" + appid +
        "&secret=" + secret +
        "&js_code=" + code +
        "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        
        String result = restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(result);

        String openid = jsonNode.get("openid").asText();
        return openid;
    }
    public String getPhone(String code){
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken;
        String result = webClient.post()
            .uri(url)
            .bodyValue("{\"code\":\"" + code + "\"}")
            .retrieve()
            .bodyToMono(String.class)
            .block();
        
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(result);

        // 👉 错误处理（非常重要）
        if (jsonNode.has("errcode") && jsonNode.get("errcode").asInt() != 0) {
            throw new RuntimeException("获取手机号失败: " + result);
        }

        return jsonNode.get("phone_info").get("phoneNumber").asText();
    }

    // 获取access_token的方法，微信服务器返回的access_token有一定的有效期，过期后需要重新获取
    public String getAccessToken(){
        String url = "https://api.weixin.qq.com/cgi-bin/token"
            + "?grant_type=client_credential"
            + "&appid=" + appid
            + "&secret=" + secret;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(result);

        accessToken = jsonNode.get("access_token").asText();
        return accessToken;
    }

    // 通过密钥拉起微信支付界面
public JsapiServiceExtension prepayWithRequestPayment(String orderNo, Integer totalAmount, String openId) {
    Config config = new RSAPublicKeyConfig.Builder()
            .merchantId(MerchantId)
            .privateKeyFromPath(privateKeyPath)
            .merchantSerialNumber(merchantSerialNumber)
            .apiV3Key(apiV3Key)
            .publicKeyId(publicKeyId)
            .publicKeyFromPath(publicKeyPath)
            .build();

    JsapiServiceExtension jsapiServiceExtension = new JsapiServiceExtension.Builder()
            .config(config)
            .signType("RSA")
            .build();
    

    return jsapiServiceExtension;
}

    // 通过密钥拉起微信退款界面
    public void refundWithRequestRefund(String trade_no, String refund_no, Integer refundAmount) {
        Config config = new RSAPublicKeyConfig.Builder()
                .merchantId(MerchantId)
                .privateKeyFromPath(privateKeyPath)
                .merchantSerialNumber(merchantSerialNumber)
                .apiV3Key(apiV3Key)
                .publicKeyId(publicKeyId)
                .publicKeyFromPath(publicKeyPath)
                .build();

        // 退款逻辑，调用微信退款接口
        RefundService refundService = new RefundService.Builder()
                .config(config)
                .build();
        CreateRequest request = new CreateRequest();
        request.setOutTradeNo(trade_no);
        request.setOutRefundNo(refund_no);
        request.setNotifyUrl("/refund/notify");
        AmountReq amount = new AmountReq();
        amount.setRefund((long) (refundAmount*100));
        amount.setTotal((long) (refundAmount*100));
        request.setAmount(amount);
        refundService.create(request);
    }
}
