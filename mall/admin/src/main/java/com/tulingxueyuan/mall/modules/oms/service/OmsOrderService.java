package com.tulingxueyuan.mall.modules.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.OrderConditonDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 */
public interface OmsOrderService extends IService<OmsOrder> {

    Page list(OrderConditonDTO condition);

    boolean delete(List<Long> ids);
}
