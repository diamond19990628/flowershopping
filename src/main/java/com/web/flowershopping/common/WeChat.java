package com.web.flowershopping.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
@Service
public class WeChat {
    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.secret}")
    private String secret;

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
        if(accessToken != null && System.currentTimeMillis() < expireTime){
            return accessToken;
        }else{
            String url = "https://api.weixin.qq.com/cgi-bin/token"
                + "?grant_type=client_credential"
                + "&appid=" + appid
                + "&secret=" + secret;
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(result);

            accessToken = jsonNode.get("access_token").asText();
            expireTime = System.currentTimeMillis() + jsonNode.get("expires_in").asLong() * 1000;
            return accessToken;
        }
    }
}
