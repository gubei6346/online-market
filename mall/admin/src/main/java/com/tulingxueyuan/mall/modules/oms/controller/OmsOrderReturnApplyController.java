package com.tulingxueyuan.mall.modules.oms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.common.api.CommonPage;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.OrderReturnApplyConditionDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrderReturnApply;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderReturnApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 订单退货申请 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/returnApply")
public class OmsOrderReturnApplyController {

    @Autowired
    OmsOrderReturnApplyService orderReturnApplyService;

    /**
     * returnApply/list?pageNum=1&pageSize=10
     * pageNum: 1,
     * pageSize: 10,
     * id: null,
     * receiverKeyword: null,
     * status: null,
     * createTime: null,
     * handleMan: null,
     * handleTime: null
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list(OrderReturnApplyConditionDTO condition) {
        Page page = orderReturnApplyService.list(condition);
        return CommonResult.success(CommonPage.restPage(page));
    }

    /**
     * url:'/returnApply/'+id,
     * method:'get'
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResult getDetail(@PathVariable Long id) {
        OmsOrderReturnApply result = orderReturnApplyService.getById(id);
        return CommonResult.success(result);
    }

    /**
     * 删除订单
     * /returnApply/delete?ids=3
     */
    @PostMapping(value = "/delete")
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        boolean result = orderReturnApplyService.delete(ids);
        if (result) {
            return CommonResult.success(result);
        } else {
            return CommonResult.failed();
        }
    }
}

