package com.tulingxueyuan.mall.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.dto.PmsProductCategoryDTO;
import com.tulingxueyuan.mall.dto.ProductCateChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产品分类 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/productCategory")
public class PmsProductCategoryController {
    @Autowired
    PmsProductCategoryService productCategoryService;
//    url:'/productCategory/list/'+parentId,
//    method:'get',
//    params:params{
//        pageNum:1,
//        pageSize:5
//    },
    //下一级
    @RequestMapping(value = "/list/{parentId}",method = RequestMethod.GET )
    public CommonResult<CommonPage<PmsProductCategory>>getList(@PathVariable Long parentId,
                                                               @RequestParam(value = "pageNum",defaultValue = "1") Integer  pageNum,
                                                               @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){

       Page page= productCategoryService.list(parentId,pageNum,pageSize);
       return CommonResult.success(CommonPage.restPage(page));
    }
   //导航栏按钮
    @RequestMapping(value = "/update/navStatus",method = RequestMethod.POST )
    public CommonResult updataNavStatus(@RequestParam(value = "ids") List<Long> ids,
                                        @RequestParam(value = "navStatus") Integer navStatus){

       boolean result= productCategoryService.updateNavStatus(ids,navStatus);
      if(result){
        return CommonResult.success(result);
      }
      else{
        return  CommonResult.failed();
      }
    }
    //是否显示按钮
    @RequestMapping(value = "/update/showStatus",method = RequestMethod.POST )
    public CommonResult updateShowStatus(@RequestParam(value = "ids") List<Long> ids,
                                         @RequestParam(value = "showStatus") Integer showStatus){

        boolean result=productCategoryService.updateShowStatus(ids,showStatus);
        if(result){
            return CommonResult.success(result);
        }
        else{
            return  CommonResult.failed();
        }

    }
    //商品分类删除
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST )
    public CommonResult delete(@PathVariable long id){
        boolean result=productCategoryService.removeById(id);
        if(result){
            return CommonResult.success(result);
        }
        else{
            return  CommonResult.failed();
        }

    }
    //分类添加
    @RequestMapping(value = "/create",method = RequestMethod.POST )
    public CommonResult create(@RequestBody PmsProductCategoryDTO productCategoryDTO){
        boolean result=productCategoryService.CustomSave(productCategoryDTO);
        if(result){
            return CommonResult.success(result);
        }
        else{
            return  CommonResult.failed();
        }

    }
    //根据id获取商品分类
    @RequestMapping(value = "//{id}",method = RequestMethod.GET )
    public CommonResult<PmsProductCategory>getById(@PathVariable Long id){

        PmsProductCategory productCategory= productCategoryService.getById(id);
        return CommonResult.success(productCategory);
    }
    //修改商品属性
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST )
    public CommonResult update(
            @RequestBody PmsProductCategoryDTO productCategoryDTO){
        boolean result=productCategoryService.update(productCategoryDTO);
        if(result){
            return CommonResult.success(result);
        }
        else{
            return  CommonResult.failed();
        }

    }

    //获取商品一级分类和二级分类的下拉级联数据
    @RequestMapping("/list/withChildren")
    public CommonResult getwithChildren(){
       List<ProductCateChildrenDTO> list= productCategoryService.getWithChildren();
        return CommonResult.success(list);
    }
}

