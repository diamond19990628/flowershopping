package com.web.flowershopping.manager.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.web.flowershopping.common.Exception.CreateException;
import com.web.flowershopping.manager.Entity.AttachedFIlePhoto;
import com.web.flowershopping.manager.Entity.Category;
import com.web.flowershopping.manager.Entity.Product;
import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Mapper.ProductMapper;

import jakarta.annotation.Resource;

@Service
public class ProductServiceImp implements ProductService{
    @Resource
    ProductMapper productmapper;

    @Value("${upload.path}")
    String upload_path;

    public Result selectAllProduct(String product_name,Integer status,boolean Low_Stock){
        Product productReadDTO = new Product();
        if(product_name != null){
            productReadDTO.setProductName(product_name);
        }
        productReadDTO.setStatus(status);
        List<Product> productListResult = productmapper.selectAllProduct(productReadDTO,Low_Stock);
        Result result = new Result();
        result.setData(productListResult);
        result.setStatus(200);
        result.setMsg("success");
        return result;
    }

    @Transactional
    public Result createProduct(String product_name,Integer amount,Integer stock,Integer category,MultipartFile file){
        
        File dir = new File(upload_path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        // 文件上传
        String filename = file.getOriginalFilename();
        File dest = new File(upload_path + filename);
        try {
        file.transferTo(dest);
        } catch (IOException e) {
            throw new CreateException("文件上传失败");
        }
        Product productCreateDTO = new Product();
        AttachedFIlePhoto attachedFilePhoto = new AttachedFIlePhoto();
        attachedFilePhoto.setAttachedFilePath(upload_path);
        attachedFilePhoto.setEntryDate(LocalDateTime.now());
        attachedFilePhoto.setUpdateDate(LocalDateTime.now());
        // 添加图片文件进数据库
        productmapper.createAttachedFile(attachedFilePhoto);
        int attached_file_id = attachedFilePhoto.getAttachedFileId();
        productCreateDTO.setAttachedFileId(attached_file_id);
        productCreateDTO.setProductName(product_name);
        productCreateDTO.setStatus(1);
        productCreateDTO.setAmount(amount);
        // 商品创建
        productmapper.createProduct(productCreateDTO);
        int product_id = productCreateDTO.getProductId();
        // 存储库存
        Product createProductCategoryDTO = new Product();
        createProductCategoryDTO.setProductId(product_id);
        createProductCategoryDTO.setStock(stock);
        productmapper.createStock(createProductCategoryDTO);
        // category
        Category categoryDTO = new com.web.flowershopping.manager.Entity.Category();
        Category categoryResult = productmapper.selectCategory(category);
        if(categoryResult == null){
            throw new CreateException("category不存在");
        }
        categoryDTO.setCategoryId(category);
        productmapper.createProductCategory(categoryDTO, productCreateDTO);
        Result result = new Result();
        result.setStatus(200);
        Map<String,Object> Resultdata = new HashMap<String,Object>();
        Resultdata.put("product_id", product_id);
        Resultdata.put("product_name", productCreateDTO.getProductName());
        Resultdata.put("category_id", categoryDTO.getCategoryId());
        Resultdata.put("category_name", categoryResult.getCategoryName());
        Resultdata.put("amount",productCreateDTO.getAmount());
        Resultdata.put("Stock",createProductCategoryDTO.getStock());
        Resultdata.put("attached_file_path", upload_path+filename);
        result.setData(Resultdata);
        return result;
    }
}
