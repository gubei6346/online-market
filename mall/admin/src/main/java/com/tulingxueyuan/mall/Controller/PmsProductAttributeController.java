package com.tulingxueyuan.mall.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 前端控制器
 * </p>*
 */
@RestController
@RequestMapping("/productAttribute")
public class PmsProductAttributeController {
@Autowired
    PmsProductAttributeService productAttributeService;
    @RequestMapping(value = "/list/{cid}",method = RequestMethod.GET )
    public CommonResult<CommonPage> getList(@PathVariable Long cid,
                                                                @RequestParam(value = "type") Integer  type,
                                                                @RequestParam(value = "pageNum",defaultValue = "1") Integer  pageNum,
                                                                @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){

        Page page= productAttributeService.list(cid,type,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(page));
    }
    //属性添加
    @RequestMapping(value = "/create",method = RequestMethod.POST )
    public CommonResult create(@RequestBody PmsProductAttribute productAttribute){
        boolean result=productAttributeService.create(productAttribute);
        if(result){
            return CommonResult.success(result);
        }
        else{
            return  CommonResult.failed();
        }

    }
    //属性修改
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST )
    public CommonResult update(@RequestBody PmsProductAttribute productAttribute){
        boolean result=productAttributeService.updateById(productAttribute);
        if(result){
            return CommonResult.success(result);
        }
        else{
            return  CommonResult.failed();
        }

    }
    //根据id获取商品属性
    @RequestMapping(value = "//{id}",method = RequestMethod.GET )
    public CommonResult<PmsProductAttribute>getById(@PathVariable Long id){

        PmsProductAttribute productCategory= productAttributeService.getById(id);
        return CommonResult.success(productCategory);
    }
    //属性删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST )
    public CommonResult delete(@RequestParam("ids") List<Long> ids){
        boolean result=productAttributeService.removeByIds(ids);
        if(result){
            return CommonResult.success(result);
        }
        else{
            return  CommonResult.failed();
        }

    }

    //根据商品的分类id获取关联的筛选属性
    @RequestMapping(value = "/attrInfo/{cId}")
    public CommonResult getRelationAttrInfoByCid(@PathVariable Long cId){
       List<RelationAttrInfoDTO> list= productAttributeService.getRelationAttrInfoByCid(cId);
       return CommonResult.success(list);
    }
}

