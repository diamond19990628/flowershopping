package com.web.flowershopping.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class getImagePath {
    @Value("${upload.path}")
    String root_path;

    @Value("${url.path}")
    String url_path;

    @Value("${image.path}")
    String image_path;

    public String changeImagePath(String db_path){
        String relativePath = db_path.replace(root_path,"");

        String imageUrl = url_path+ image_path+ relativePath;
        return imageUrl;
    }
}
