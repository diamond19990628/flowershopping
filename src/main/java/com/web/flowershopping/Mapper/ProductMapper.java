package com.web.flowershopping.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.Entity.AttachedFIlePhoto;
import com.web.flowershopping.Entity.Card;
import com.web.flowershopping.Entity.Category;
import com.web.flowershopping.Entity.Product;

@Mapper
public interface ProductMapper {
    List<Product> selectAllProduct(
        @Param("product") Product product,
        @Param("Low_Stock") boolean Low_Stock
    );

    Product selectProductWithRequestNo(@Param("requestNo") String requestNo);

    List<Product> selectProductWithCategoryID(
        @Param("category_id") Integer category_id,
        @Param("access_flag") int access_flag
    );

    Product selectProductWithID(@Param("product_id")Integer product_id);

    int createAttachedFile(
        @Param("attachedFile") AttachedFIlePhoto attachedFIlePhoto
    );
    
    void deleteAttachedFile(
        @Param("attachedFile") AttachedFIlePhoto attachedFIlePhoto
    );

    int createProductCategory(
        @Param("category") Category category,
        @Param("product") Product product
    );
    int createProduct(
        @Param("product") Product product
    );

    void updateProduct(
        @Param("product") Product product
    );

    void updateProductStatus(
        @Param("product") Product product
    );

    void createStock(@Param("product") Product product);

    void updateStock(@Param("product") Product product);

    Category selectCategory(@Param("categoryId") int category_id);

    void updateProductCategory(@Param("product") Product product);

    // 查询是否有库存
    Integer checkStock(@Param("product_id") int product_id);

    // 查询所有卡片
    List<Card> selectAllCard();

    void deleteProduct(@Param("product_id") Integer product_id);
}
