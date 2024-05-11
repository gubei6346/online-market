package com.tulingxueyuan.mall.modules.oms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.OrderReturnReasonAddConditionDTO;
import com.tulingxueyuan.mall.dto.OrderReturnReasonConditionDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrderReturnReason;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderReturnReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 退货原因表 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/returnReason")
public class OmsOrderReturnReasonController {

    @Autowired
    OmsOrderReturnReasonService omsOrderReturnReasonService;

    /**
     * 原因设置列表
     * /list?pageNum=1&pageSize=10
     * pageNum: 1,
     * pageSize: 10,
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(OrderReturnReasonConditionDTO condition) {
        Page page = omsOrderReturnReasonService.list(condition);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * 增加原因
     * url:'/returnReason/create',
     * method:'post',
     * <p>
     * name:null,
     * sort:0,
     * status:1,
     * createTime:null
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CommonResult add(@RequestBody OrderReturnReasonAddConditionDTO condition) {
        boolean reasult = omsOrderReturnReasonService.add(condition);
        return CommonResult.success(reasult);
    }

    /**
     * 更新是否可用
     * /returnReason/update/status?status=1&ids=1
     */
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public CommonResult updateStatus(@RequestParam(value = "status", defaultValue = "") Integer status,
                                     @RequestParam(value = "ids", defaultValue = "") Long ids) {
        boolean reasult = omsOrderReturnReasonService.updateStatus(status, ids);
        return CommonResult.success(reasult);
    }

    /**
     * 删除
     * /returnReason/delete?ids=18
     */
    @PostMapping(value = "/delete")
    public CommonResult delete(@RequestParam(value = "ids") List<Long> ids) {
        boolean reasult = omsOrderReturnReasonService.delete(ids);
        return CommonResult.success(reasult);
    }

    /**
     * 原因设置详情
     * /returnReason/5
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult detail(@PathVariable Long id) {
        OmsOrderReturnReason orderReturnReason = omsOrderReturnReasonService.getById(id);
        return CommonResult.success(orderReturnReason);
    }

    /**
     * 原因设置更新
     * /returnReason/update/5
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public CommonResult updateDetail(@RequestBody OmsOrderReturnReason orderReturnReason) {
        boolean reasult = omsOrderReturnReasonService.updateById(orderReturnReason);
        return CommonResult.success(reasult);
    }


}

