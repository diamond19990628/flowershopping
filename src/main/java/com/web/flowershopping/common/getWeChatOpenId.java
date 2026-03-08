package com.web.flowershopping.common;

import org.springframework.web.client.RestTemplate;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class getWeChatOpenId {
    public String getWechatOpenId(String code){
        String appid = "wx98489d645aa4a8f3";
        String secret = "c6d37767f03fcde4bcc238a11b2bee6b";
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
}
