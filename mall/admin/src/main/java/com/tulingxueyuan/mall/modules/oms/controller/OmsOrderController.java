package com.tulingxueyuan.mall.modules.oms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.OrderConditonDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 订单表 前端控制器
 * </p>

 */
@RestController
@RequestMapping("/order")
public class OmsOrderController {
    @Autowired
    OmsOrderService orderService;


    /**
     * 订单列表
     * /list?pageNum=1&pageSize=10
     * pageNum: 1,
     * pageSize: 10,
     * orderSn: null,
     * receiverKeyword: null,
     * status: null,
     * orderType: null,
     * sourceType: null,
     * createTime: null,
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(OrderConditonDTO condition) {
        Page page = orderService.list(condition);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 订单详情
     * /order/12
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult detail(@PathVariable Long id) {
        OmsOrder result = orderService.getById(id);
        return CommonResult.success(result);
    }

    /**
     * 删除订单
     * /order/delete?ids=12
     */
    @PostMapping(value = "/delete")
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        boolean result = orderService.delete(ids);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }
}

