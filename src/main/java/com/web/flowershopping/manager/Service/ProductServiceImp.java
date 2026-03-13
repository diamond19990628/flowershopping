package com.web.flowershopping.manager.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.web.flowershopping.common.getImagePath;
import com.web.flowershopping.common.Exception.CreateException;
import com.web.flowershopping.common.Exception.ReadException;
import com.web.flowershopping.manager.Entity.AttachedFIlePhoto;
import com.web.flowershopping.manager.Entity.Category;
import com.web.flowershopping.manager.Entity.Product;
import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Mapper.CategoryMapper;
import com.web.flowershopping.manager.Mapper.ProductMapper;

import jakarta.annotation.Resource;

@Service
public class ProductServiceImp implements ProductService{
    @Resource
    ProductMapper productmapper;

    @Resource
    CategoryMapper categoryMapper;

    @Resource
    getImagePath getimagePath;

    @Value("${upload.path}")
    String upload_path;

    public Result selectAllProduct(String product_name,Integer status,boolean Low_Stock){
        Product productReadDTO = new Product();
        if(product_name != null){
            productReadDTO.setProductName(product_name);
        }
        productReadDTO.setStatus(status);
        List<Product> productListResult = productmapper.selectAllProduct(productReadDTO,Low_Stock);
        for(Product product : productListResult){
            String imagePath = getimagePath.changeImagePath(product.getAttachedFile().getAttachedFilePath());
            product.getAttachedFile().setAttachedFilePath(imagePath);
        }
        Result result = new Result();
        result.setData(productListResult);
        result.setStatus(200);
        result.setMsg("success");
        return result;
    }

    public Result selectProductWithID(Integer product_id){
        Product productListResult = productmapper.selectProductWithID(product_id);
        if(productListResult==null){
            throw new ReadException("商品不存在或者已经被删除");
        }
        String imagePath = getimagePath.changeImagePath(productListResult.getAttachedFile().getAttachedFilePath());
        AttachedFIlePhoto newAttachedFile = new AttachedFIlePhoto();
        newAttachedFile.setAttachedFileId(productListResult.getAttachedFileId());
        newAttachedFile.setAttachedFilePath(imagePath);
        productListResult.setAttachedFile(newAttachedFile);
        Result result = new Result();
        result.setData(productListResult);
        result.setStatus(200);
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
        attachedFilePhoto.setAttachedFilePath(upload_path+filename);
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
        // response
        Map<String,Object> Resultdata = new HashMap<String,Object>();
        Resultdata.put("productId", product_id);
        Resultdata.put("productName", productCreateDTO.getProductName());
        Category categoryInfo = new Category();
        categoryInfo.setCategoryId(categoryDTO.getCategoryId());
        categoryInfo.setCategoryName(categoryResult.getCategoryName());
        Resultdata.put("category",categoryInfo);
        Resultdata.put("amount",productCreateDTO.getAmount());
        Resultdata.put("stock",createProductCategoryDTO.getStock());
        AttachedFIlePhoto attachedFileResult = new AttachedFIlePhoto();
        attachedFileResult.setAttachedFileId(attached_file_id);
        String imagePath = getimagePath.changeImagePath(upload_path + filename);
        attachedFileResult.setAttachedFilePath(imagePath);
        Resultdata.put("attachedFile",attachedFileResult);
        result.setData(Resultdata);
        return result;
    }

    // 更新商品信息
    @Transactional
    public Result updateProduct(Integer product_id,String product_name,Integer amount,Integer stock,Integer category,MultipartFile file){
        // 查询现有的情报
        Product productInfoResult = productmapper.selectProductWithID(product_id);
        if(productInfoResult==null){
            throw new ReadException("该产品已经被删除，无法更改");
        }
        AttachedFIlePhoto attachedReadDTO = new AttachedFIlePhoto();
        attachedReadDTO.setAttachedFileId(productInfoResult.getAttachedFile().getAttachedFileId());
        attachedReadDTO.setAttachedFilePath(productInfoResult.getAttachedFile().getAttachedFilePath());
        // 删除老的图片(数据库)
        productmapper.deleteAttachedFile(attachedReadDTO);
        Path filepath = Paths.get(attachedReadDTO.getAttachedFilePath());
        try{
            Files.deleteIfExists(filepath);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"删除图片失败");
        }
        // 文件上传
        String filename = file.getOriginalFilename();
        File dest = new File(upload_path + filename);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new CreateException("文件上传失败");
        }
        // 文件数据库写入
        AttachedFIlePhoto attachedFIleUpdateDTO = new AttachedFIlePhoto();
        attachedFIleUpdateDTO.setAttachedFilePath(upload_path+filename);
        attachedFIleUpdateDTO.setEntryDate(LocalDateTime.now());
        attachedFIleUpdateDTO.setUpdateDate(LocalDateTime.now());
        productmapper.createAttachedFile(attachedFIleUpdateDTO);
        // 检查category是否存在
        Category categoryResult = categoryMapper.selectCategoryWithID(category);
        if(categoryResult == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"分类已经被删除，无法修改");
        }

        // 更新产品信息
        Product productUpdateDTO = new Product();
        productUpdateDTO.setProductId(product_id);
        productUpdateDTO.setProductName(product_name);
        productUpdateDTO.setAttachedFile(attachedFIleUpdateDTO);
        productUpdateDTO.setStock(stock);
        productUpdateDTO.setAmount(amount);
        productUpdateDTO.setCategory(categoryResult);
        // 更新主表
        productmapper.updateProduct(productUpdateDTO);
        // 更新库存
        productmapper.updateStock(productUpdateDTO);
        // 更新category
        productmapper.updateProductCategory(productUpdateDTO);
        Result result = new Result();
        // 设置response
        Map<String,Object> Resultdata = new HashMap<String,Object>();
        Resultdata.put("productId", product_id);
        Resultdata.put("productName", product_name);
        Category categoryInfo = new Category();
        categoryInfo.setCategoryId(categoryResult.getCategoryId());
        categoryInfo.setCategoryName(categoryResult.getCategoryName());
        Resultdata.put("category",categoryInfo);
        Resultdata.put("amount",amount);
        Resultdata.put("stock",stock);
        AttachedFIlePhoto attachedFileResult = new AttachedFIlePhoto();
        attachedFileResult.setAttachedFileId(productInfoResult.getAttachedFile().getAttachedFileId());
        String imagePath = getimagePath.changeImagePath(upload_path + filename);
        attachedFileResult.setAttachedFilePath(imagePath);
        Resultdata.put("attachedFile",attachedFileResult);
        result.setData(Resultdata);
        result.setStatus(200);
        return result;
    }
}
