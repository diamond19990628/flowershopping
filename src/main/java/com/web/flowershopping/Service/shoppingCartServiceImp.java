package com.web.flowershopping.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Entity.shoppingCart;
import com.web.flowershopping.Mapper.ShoppingCartMapper;
import com.web.flowershopping.common.getImagePath;

import jakarta.annotation.Resource;
@Service
public class shoppingCartServiceImp implements shoppingCartService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @Resource
    getImagePath getimagePath;

    @Override
    public Result selectCartItemsByUserId(int userId) {
        // TODO Auto-generated method stub
        List<shoppingCart> cartItems = shoppingCartMapper.selectCartItemsByUserId(userId);
        for (shoppingCart item : cartItems) {
            String dbImagePath = item.getAttachedFilePath();
            String imageUrl = getimagePath.changeImagePath(dbImagePath);
            item.setAttachedFilePath(imageUrl);
        }
        Result result = new Result();
        result.setStatus(200);
        result.setData(cartItems);
        result.setMsg("success");
        return result;
    }
    
}
