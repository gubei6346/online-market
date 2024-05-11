package com.tulingxueyuan.mall.modules.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.OrderReturnApplyConditionDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrderReturnApply;

import java.util.List;

/**
 * <p>
 * 订单退货申请 服务类
 * </p>
 *
 */
public interface OmsOrderReturnApplyService extends IService<OmsOrderReturnApply> {

    Page list(OrderReturnApplyConditionDTO condition);

    boolean delete(List<Long> ids);
}
