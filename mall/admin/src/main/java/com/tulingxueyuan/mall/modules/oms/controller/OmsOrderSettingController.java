package com.tulingxueyuan.mall.modules.oms.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrderSetting;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单设置表 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/orderSetting")
public class OmsOrderSettingController {

    @Autowired
    OmsOrderSettingService orderSettingService;


    @GetMapping(value = "/{id}")
    public CommonResult get(@PathVariable Long id) {
        QueryWrapper<OmsOrderSetting> orderSettingQueryWrapper = new QueryWrapper<>();
        orderSettingQueryWrapper.lambda().eq(OmsOrderSetting::getSettingId, id);
        OmsOrderSetting result = orderSettingService.getOne(orderSettingQueryWrapper);
        return CommonResult.success(result);
    }

    /**
     * url:'/orderSetting/update/'+id,
     * method:'post',
     * data:data
     * <p>
     * id: null,
     * flashOrderOvertime: 0,
     * normalOrderOvertime: 0,
     * confirmOvertime: 0,
     * finishOvertime: 0,
     * commentOvertime: 0
     */

    @PostMapping(value = "/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody OmsOrderSetting orderSetting) {
        boolean result = orderSettingService.update(id, orderSetting);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }
}

