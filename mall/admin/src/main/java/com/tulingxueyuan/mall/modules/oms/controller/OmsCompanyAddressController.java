package com.tulingxueyuan.mall.modules.oms.controller;


import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.oms.model.OmsCompanyAddress;
import com.tulingxueyuan.mall.modules.oms.service.OmsCompanyAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 公司收发货地址表 前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/companyAddress")
public class OmsCompanyAddressController {

    @Autowired
    OmsCompanyAddressService companyAddressService;

    /**
     * 订单列表
     * /companyAddress/list
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult list() {
        List<OmsCompanyAddress> result = companyAddressService.list();
        return CommonResult.success(result);
    }
}

