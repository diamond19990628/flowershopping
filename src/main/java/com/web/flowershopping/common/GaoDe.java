package com.web.flowershopping.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.web.flowershopping.common.Exception.ParamException;

import tools.jackson.databind.JsonNode;
@Service
public class GaoDe {
    @Value("${gaode.key}")
    private String key;
    @Value("${origin}")
    private String origin;

    public Map<String, Integer> getAddress(String address){
        // 调用高德地图API获取地址信息
        JsonNode result = WebClient.builder()
                .baseUrl("https://restapi.amap.com")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v3/geocode/geo")
                        .queryParam("address", address)
                        .queryParam("key", key)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        if(result.get("geocodes") == null || result.get("geocodes").size() == 0){
            throw new ParamException("地址解析失败，请检查地址是否正确");
        }
        
        String location = result.get("geocodes").get(0).get("location").asText();
        System.out.println(location);

        JsonNode locationResult = WebClient.builder()
                .baseUrl("https://restapi.amap.com")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v3/direction/driving")
                        .queryParam("origin", location)
                        .queryParam("destination", origin)
                        .queryParam("key", key)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        String distance = locationResult.get("route").get("paths").get(0).get("distance").asText();
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("distance", Integer.valueOf(distance));
        return resultMap;
    }
}
