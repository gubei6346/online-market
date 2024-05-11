package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.AddCarDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsCartItem;
import com.tulingxueyuan.mall.modules.oms.service.OmsCartItemService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "carController",description = "购物车服务接口")
@RequestMapping("/car")
public class CarController {
    @Autowired
    OmsCartItemService cartItemService;
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public CommonResult add(@RequestBody AddCarDTO addCarDTO){
        Boolean result=cartItemService.add(addCarDTO);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }
    //获取购物数据初始化
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public CommonResult getList(){
       List<OmsCartItem>  list= cartItemService.getList();
        return CommonResult.success(list);
    }
    //更新商品数量
    @RequestMapping(value = "/update/quantity",method = RequestMethod.POST)
    public CommonResult updateQuantity(
            @RequestParam Long id,
            @RequestParam Integer quantity){
       Boolean result= cartItemService.updateQuantity(id,quantity);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }
    //删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public CommonResult updateQuantity(
            @RequestParam Long ids){
        Boolean result= cartItemService.delete(ids);
        if (result){
            return CommonResult.success(result);
        }else {
            return CommonResult.failed();
        }
    }
    @RequestMapping(value = "/products/sum",method = RequestMethod.GET)
    public void sum(){
    }
}
