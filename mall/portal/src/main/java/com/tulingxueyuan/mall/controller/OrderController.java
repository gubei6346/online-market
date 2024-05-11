package com.tulingxueyuan.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.ConfirmOrderDTo;
import com.tulingxueyuan.mall.dto.OrderDetailDTO;
import com.tulingxueyuan.mall.dto.OrderListDTO;
import com.tulingxueyuan.mall.dto.OrderParamDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "OrederController",description = "订单服务接口")
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OmsOrderService orderService;
    //初始化确认订单商品信息
    @RequestMapping(value = "/generateConfirmOrder",method = RequestMethod.POST)
    public CommonResult generateConfirmOrder(
            @RequestParam("itemIds") List<Long> ids
    ){
        ConfirmOrderDTo confirmOrderDTo= orderService.generateConfirmOrder(ids);
        return CommonResult.success(confirmOrderDTo);
    }
    //生成订单
    @RequestMapping(value="/generateOrder",method = RequestMethod.POST)
    public CommonResult generateOrder(@RequestBody OrderParamDTO paramDTO){
        OmsOrder omsOrder = orderService.generateOrder(paramDTO);
        return CommonResult.success(omsOrder.getId());
        //return null ;
    }
    @RequestMapping(value="/orderDetail",method = RequestMethod.POST)
    public CommonResult generateDetail(@RequestBody OrderParamDTO paramDTO){

        return null ;
    }
}
