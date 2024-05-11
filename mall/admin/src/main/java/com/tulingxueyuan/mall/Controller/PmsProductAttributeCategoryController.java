package com.tulingxueyuan.mall.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.dto.ProductAttributeCateDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品属性分类表 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/productAttribute/category")
public class PmsProductAttributeCategoryController {

    @Autowired
    PmsProductAttributeCategoryService attributeCategoryService;
//查询商品分类列表
    @RequestMapping(value = "/list",method = RequestMethod.GET )
    public CommonResult<CommonPage<PmsProductAttributeCategory>> getList(
                                                            @RequestParam(value = "pageNum",defaultValue = "1") Integer  pageNum,
                                                            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){

    Page page= attributeCategoryService.list(pageNum,pageSize);
    return CommonResult.success(CommonPage.restPage(page));
    }
//添加商品类型
    @RequestMapping(value = "/create",method = RequestMethod.POST )
        public CommonResult create( PmsProductAttributeCategory productAttributeCategory){
            boolean result=attributeCategoryService.add(productAttributeCategory);
             if(result){
            return CommonResult.success(result);
             }
             else{
            return  CommonResult.failed();
             }

    }
    //商品类型编辑
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST )
    public CommonResult update( PmsProductAttributeCategory productAttributeCategory){
        boolean result=attributeCategoryService.updateById(productAttributeCategory);
        if(result){
            return CommonResult.success(result);
        }
        else{
            return  CommonResult.failed();
        }

    }
    //商品类型删除
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET )
    public CommonResult delete(@PathVariable long id){
        boolean result=attributeCategoryService.removeById(id);
        if(result){
            return CommonResult.success(result);
        }
        else{
            return  CommonResult.failed();
        }

    }
    //筛选属性下拉级联数据
    @RequestMapping(value ="/list/withAttr",method = RequestMethod.GET)
    public CommonResult getListWithAttr(){
        List<ProductAttributeCateDTO> list= attributeCategoryService.getListWithAttr();
        return CommonResult.success(list);
    }

}

