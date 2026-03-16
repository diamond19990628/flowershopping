package com.web.flowershopping.manager.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.flowershopping.common.sessions;
import com.web.flowershopping.manager.Entity.Product;
import com.web.flowershopping.manager.Entity.Result;
import com.web.flowershopping.manager.Service.CategoryService;
import com.web.flowershopping.manager.Service.ProductService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;




@RestController
public class indexController {
    @Resource
    ProductService productservice;

    @Resource
    CategoryService categoryService;

    // 产品管理专属
    // 产品情报获取all
    @GetMapping("/product")
    public Result productGateway(HttpServletRequest request,@RequestParam(required = false)String product_name,@RequestParam(defaultValue = "0")Integer status,@RequestParam(defaultValue = "false")boolean Low_Stock){
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = productservice.selectAllProduct(product_name,status,Low_Stock);
        return result;
    }

    // 产品情报获取（指定ID）
    @GetMapping("/product/{product_id}")
    public Result getMethodName(HttpServletRequest request,@PathVariable("product_id") Integer productId) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = productservice.selectProductWithID(productId);
        return result;
    }
    
    // 新建产品
    @PostMapping("/product")
    public Result createProduct(HttpServletRequest request,
                                @RequestParam("product_name") String productName,
                                @RequestParam("amount") Integer amount,
                                @RequestParam("stock") Integer stock,
                                @RequestParam("category") Integer category_id,
                                @RequestParam("attached_file") MultipartFile attached_file
                                ) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = new Result();
        result = productservice.createProduct(productName,amount,stock,category_id,attached_file);
        return result;
    }
    
    // 更新产品（POST）
    @PostMapping("/product/{product_id}")
    public Result updateProduct(HttpServletRequest request,
                                @PathVariable("product_id")Integer product_id,
                                @RequestParam("product_name") String productName,
                                @RequestParam("amount") Integer amount,
                                @RequestParam("stock") Integer stock,
                                @RequestParam("category") Integer category_id,
                                @RequestParam(value = "attached_file",required = false) MultipartFile attached_file                        
                                ) {
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = productservice.updateProduct(product_id, productName, amount, stock, category_id, attached_file);
        return result;
    }

    // 下架产品（PATCH）
    @PatchMapping("/product/{product_id}")
    public Result changeProductStatus(
        HttpServletRequest request,
        @PathVariable("product_id")Integer product_id,
        @RequestBody Product product
    ){
        String token = request.getHeader("token");
        sessions.auth_session(request, token);
        Result result = productservice.UnlistProduct(product_id, product.getStatus());
        return result;

    }

    // category专属
    @GetMapping("/categories")
    public Result getCategory(HttpServletRequest request) {
        // String token = request.getHeader("token");
        // sessions.auth_session(request, token);
        Result result = categoryService.selectAllCategory();
        return result;
    }

    // 获取所有分类（父类和子类）
    @GetMapping("/categories/all")
    public Result getAllGategory(HttpServletRequest request) {
        // String token = request.getHeader("token");
        // sessions.auth_session(request, token);
        Result result = categoryService.selectAllParentAndChildCategory();
        return result;
    }
    
    
}
