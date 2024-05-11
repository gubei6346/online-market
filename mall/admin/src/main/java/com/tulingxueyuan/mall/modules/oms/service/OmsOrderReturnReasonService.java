package com.tulingxueyuan.mall.modules.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.OrderReturnReasonAddConditionDTO;
import com.tulingxueyuan.mall.dto.OrderReturnReasonConditionDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrderReturnReason;

import java.util.List;

/**
 * <p>
 * 退货原因表 服务类
 * </p>
 *
 */
public interface OmsOrderReturnReasonService extends IService<OmsOrderReturnReason> {

    Page list(OrderReturnReasonConditionDTO condition);

    boolean add(OrderReturnReasonAddConditionDTO condition);

    boolean updateStatus(int status, Long ids);

    boolean delete(List<Long> ids);
}
