package com.web.flowershopping.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.web.flowershopping.Entity.Card;
import com.web.flowershopping.Entity.DeliveryAddress;
import com.web.flowershopping.Entity.Discount;
import com.web.flowershopping.Entity.Product;
import com.web.flowershopping.Entity.Result;
import com.web.flowershopping.Entity.shoppingCart;
import com.web.flowershopping.Mapper.DiscountMapper;
import com.web.flowershopping.Mapper.ProductMapper;
import com.web.flowershopping.Mapper.ShoppingCartMapper;
import com.web.flowershopping.common.Exception.BusinessException;
import com.web.flowershopping.common.Exception.ReadException;
import com.web.flowershopping.common.getImagePath;

import jakarta.annotation.Resource;
@Service
public class shoppingCartServiceImp implements shoppingCartService {
    @Resource
    private ShoppingCartMapper shoppingCartMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    getImagePath getimagePath;
    @Resource
    private DiscountMapper discountMapper;

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

    @Transactional
    @Override
    public Result addCartItem(int product_id, int card_id, int user_id, int quantity,String comment,Integer is_anonymous) {
        shoppingCart cartItem = new shoppingCart();
        Product product = new Product();
        product.setProductId(product_id);
        cartItem.setProduct(product);
        Card card = new Card();
        card.setCard_id(card_id);
        cartItem.setCard(card);
        cartItem.setUser_id(user_id);
        cartItem.setQuantity(quantity);
        cartItem.setComment(comment);
        cartItem.setIs_anonymous(is_anonymous);
        // 查询商品是否存在
        Product productInfo = productMapper.selectProductWithID(product_id);
        if (productInfo == null) {
            throw new ReadException("商品不存在");
        }
        // 查询是否还有库存
        Integer stock = productMapper.checkStock(product_id);
        if (stock == 0) {
            throw new BusinessException("库存不足");
        }
        // 查询该商品是否已经在购物车中，如果已经存在则更新数量，否则添加新条目
        int cartItemCount = shoppingCartMapper.selectCartItemCount(product_id, user_id);
        if (cartItemCount > 0) {
            // 已经存在，更新数量
            shoppingCartMapper.updateCartItemQuantity(product_id, user_id);
        }else{
            // 不存在，添加新条目
            int rowsAffected = shoppingCartMapper.addCartItem(cartItem);
            if (rowsAffected == 0) {
                throw new BusinessException("添加购物车失败");
            }
        }
        Result result = new Result();
        result.setStatus(200);
        result.setMsg("success");
        return result;
    }

    @Transactional
    @Override
    public Result deleteCartItem(int user_id, int product_id) {
        int rowsAffected = shoppingCartMapper.deleteCartItem(user_id, product_id);
        if (rowsAffected == 0) {
            throw new ReadException("删除购物车项失败，可能是因为该项不存在");
        }
        Result result = new Result();
        result.setStatus(204);
        result.setMsg("success");
        result.setData(rowsAffected);
        return result;
    }

    @Override
    public Result selectDeliveryAddressByUserId(int user_id) {
        List<DeliveryAddress> deliveryAddress = shoppingCartMapper.selectDeliveryAddressByUserId(user_id);
        Result result = new Result();
        result.setStatus(200);
        result.setData(deliveryAddress);
        result.setMsg("success");
        return result;
    }

    @Override
    public Result calculateTotalAmount(Map<String, Object> Requestdata) {
        // TODO Auto-generated method stub
        List<Map<String, Object>> productInfoArray = (List<Map<String, Object>>) Requestdata.get("shoppingCart");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String deliveryDate = (String)Requestdata.get("deliveryDate");
        LocalDate deliveryDateFormattered = null;
        if (deliveryDate!=null) {
            deliveryDateFormattered = LocalDate.parse(deliveryDate,formatter);
        }
        double totalAmount = 0;
        for (Map<String, Object> productInfo : productInfoArray) {
            if(productInfo.get("product")==null || productInfo.get("quantity")==null){
                throw new ReadException("product或quantity参数缺失");
            }
            Map<String, Object> product = (Map<String, Object>) productInfo.get("product");
            Integer productId = (Integer) product.get("productId");
            if(productId == null){
                throw new ReadException("productId参数缺失");
            }
            Integer quantity = (Integer) productInfo.get("quantity");
            if(quantity == null){
                throw new ReadException("quantity参数缺失");
            }
            // 查询商品金额
            Integer productAmount = productMapper.selectProductAmountByProductId(productId);
            if (productAmount == null) {
                throw new ReadException("商品不存在");
            }
            // 计算总金额
            totalAmount += productAmount * quantity;
        }
        // 2026-08-11 将最新开发的折扣功能集成到购物车总金额计算中
        // 查询所有有效的折扣信息
        double sum_discount_amount = 0;
        List<Discount> discountList = discountMapper.selectDiscountListWithScope(1);
        Map<String,Object> mapdata = new HashMap<String,Object>();
        mapdata.put("original_amount", totalAmount);
        ArrayList<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        LocalDateTime now = LocalDateTime.now();
        for(Discount discount : discountList){
            // 判断折扣是否在有效期内
            Map<String,Object> DiscountObj = new HashMap<String,Object>();
            LocalDateTime startTime = discount.getStart_date();
            LocalDateTime endTime = discount.getEnd_date();
            double discount_amount = 0;
            if(startTime == null || endTime == null){
                throw new ReadException("折扣的开始时间或结束时间为空");
            }
            if(now.isBefore(startTime) || now.isAfter(endTime)){
                continue;
            }
            Integer discountTypeId = discount.getDiscountType().getDiscount_type_id();
            if(discountTypeId == 1){
                // 全场打折折扣
                double discountRate = discount.getDiscount_rate();
                discount_amount = totalAmount-totalAmount * (discountRate/10);
                Integer discount_amount_int = (int)Math.round(discount_amount);
                sum_discount_amount += discount_amount_int;
                totalAmount = totalAmount * (discountRate/10);
                DiscountObj.put("discount_count", discount_amount_int);
                DiscountObj.put("discount_title", discount.getDiscount_title());
                resultList.add(DiscountObj);
            }else if(discountTypeId == 2){
                // 满减折扣
                double thresholdAmount = discount.getThreshold_amount();
                double reductionAmount = discount.getReduction_amount();
                if(totalAmount >= thresholdAmount){
                    totalAmount = totalAmount - reductionAmount;
                    sum_discount_amount+=reductionAmount;
                    DiscountObj.put("discount_count", reductionAmount);
                    DiscountObj.put("discount_title", discount.getDiscount_title());
                    resultList.add(DiscountObj);
                }
            }else if (discountTypeId == 3 && deliveryDateFormattered != null) {
                Integer advanceDays = discount.getAdvance_days();
                LocalDate salesDateTime = discount.getBooking_time().toLocalDate();
                if(!salesDateTime.equals(deliveryDateFormattered)){
                    continue;
                }else{
                    LocalDate afterdays = now.plusDays(advanceDays).toLocalDate();
                    if (salesDateTime.isAfter(afterdays)) {
                        double discountRate = discount.getDiscount_rate();
                        discount_amount = totalAmount-totalAmount * (discountRate/10);
                        Integer discount_amount_int = (int)Math.round(discount_amount);
                        sum_discount_amount += discount_amount_int;
                        totalAmount = totalAmount * (discountRate/10);
                        DiscountObj.put("discount_title", discount.getDiscount_title());
                        DiscountObj.put("discount_count", discount_amount_int);
                        resultList.add(DiscountObj);
                    }
                }
                
            }
            
        }
        Integer total_amount_int = (int) Math.round(totalAmount);
        mapdata.put("total_amount", total_amount_int);
        mapdata.put("discount_data", resultList);
        mapdata.put("sum_discount_amount", sum_discount_amount);
        Result result = new Result();
        result.setData(mapdata);
        result.setStatus(200);
        return result;
    }
}