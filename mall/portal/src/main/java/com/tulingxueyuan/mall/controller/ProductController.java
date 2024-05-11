package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.ProductDetailDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "ProductController",description = "商品详情页")
@RequestMapping("/product")
public class ProductController {
    @Autowired
    PmsProductService productService;
    //获取商品详情
    @RequestMapping("/detail/{id}")
    public CommonResult getProductDetail(@PathVariable Long id){
        ProductDetailDTO productDetailDTO= productService.getProductDetail(id);
        return CommonResult.success(productDetailDTO);
    }



}
