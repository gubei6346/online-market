package com.tulingxueyuan.mall.modules.oms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tulingxueyuan.mall.dto.ConfirmOrderDTo;
import com.tulingxueyuan.mall.dto.OrderDetailDTO;
import com.tulingxueyuan.mall.dto.OrderListDTO;
import com.tulingxueyuan.mall.dto.OrderParamDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 */
public interface OmsOrderService extends IService<OmsOrder> {
    //初始化确认订单的商品信息
    ConfirmOrderDTo generateConfirmOrder(List<Long> ids);

    OmsOrder generateOrder(OrderParamDTO paramDTO);
}
