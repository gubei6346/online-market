package com.tulingxueyuan.mall.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.dto.ProductConditionDTO;
import com.tulingxueyuan.mall.dto.ProductSaveParamsDTO;
import com.tulingxueyuan.mall.dto.ProductUpdateInitDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/product")
public class PmsProductController {

    @Autowired
    PmsProductService productService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
   public CommonResult list(ProductConditionDTO condition){
       Page page=productService.list(condition);
       return CommonResult.success(CommonPage.restPage(page));


   }
    @RequestMapping(value = "/update/deleteStatus",method = RequestMethod.POST)
    public CommonResult delete(@RequestParam("ids") List<Long> ids){
        boolean result = productService.removeByIds(ids);
        if(result){
            return  CommonResult.success(result);
        }else{
            return CommonResult.failed();
        }
    }
    //更新是否新品状态
    @RequestMapping(value="/update/newStatus",method = RequestMethod.POST)
    public CommonResult updateNewStatus(@RequestParam("ids") List<Long> ids,
                                        @RequestParam("newStatus") Integer newStatus) {
        boolean result= productService.updateStatus(newStatus,ids, PmsProduct::getNewStatus);
        if(result){
            return CommonResult.success(result);
        }
        else {
            return CommonResult.failed();
        }
    }
    //更新是否推荐
    @RequestMapping(value="/update/recommendStatus",method = RequestMethod.POST)
    public CommonResult updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("recommendStatus") Integer recommendStatus) {
        boolean result= productService.updateStatus(recommendStatus,ids,PmsProduct::getRecommandStatus);
        if(result){
            return CommonResult.success(result);
        }
        else {
            return CommonResult.failed();
        }
    }
    //更新是否上架
    @RequestMapping(value="/update/publishStatus",method = RequestMethod.POST)
    public CommonResult updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                            @RequestParam("publishStatus") Integer publishStatus) {
        boolean result= productService.updateStatus(publishStatus,ids,PmsProduct::getPublishStatus);
        if(result){
            return CommonResult.success(result);
        }
        else {
            return CommonResult.failed();
        }
    }
    @RequestMapping(value="/create",method = RequestMethod.POST)
    public CommonResult create(@RequestBody ProductSaveParamsDTO productSaveParamsDTO){
        boolean result= productService.create(productSaveParamsDTO);
        if(result){
            return CommonResult.success(result);
        }
        else {
            return CommonResult.failed();
        }
    }
    //获取编辑状态下商品信息
    @RequestMapping(value="/updateInfo/{id}")
    public CommonResult getUpdateInfo(@PathVariable Long id){
        ProductUpdateInitDTO updateInitDTO= productService.getUpdateInfo(id);
        return CommonResult.success(updateInitDTO);
    }
    //商品修改保存
    @RequestMapping(value="/update/{id}",method = RequestMethod.POST)
    public CommonResult update(@RequestBody @Valid ProductSaveParamsDTO productSaveParamsDTO){
        boolean result= productService.update(productSaveParamsDTO);
        if(result){
            return CommonResult.success(result);
        }
        else {
            return CommonResult.failed();
        }
    }
}

