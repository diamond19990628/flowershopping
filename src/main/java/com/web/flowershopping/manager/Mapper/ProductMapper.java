package com.web.flowershopping.manager.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.web.flowershopping.manager.Entity.AttachedFIlePhoto;
import com.web.flowershopping.manager.Entity.Category;
import com.web.flowershopping.manager.Entity.Product;

@Mapper
public interface ProductMapper {
    List<Product> selectAllProduct(
        @Param("product") Product product,
        @Param("Low_Stock") boolean Low_Stock
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
}
