package com.web.flowershopping.common;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    @Value("${image.path}")
    String image_path;

    @Value("${upload.path}")
    String upload_path;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(image_path+"**")
                .addResourceLocations("file:"+upload_path);
    }

    // 生成创建订单的token，防止重复提交订单，可以使用更复杂的token生成方式如JWT等
    public String createToken(Integer user_id){
        // 生成一个简单的token，可以使用UUID或者JWT等方式生成更复杂的token
        String token = UUID.randomUUID().toString();
        return token;
    }
}
