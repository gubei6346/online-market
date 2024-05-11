package com.tulingxueyuan.mall.modules.oms.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.dto.OrderDetailDTO;
import com.tulingxueyuan.mall.dto.OrderListDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 */
public interface OmsOrderMapper extends BaseMapper<OmsOrder> {
    OrderDetailDTO getOrderDetail(Long id);

    IPage<OrderListDTO> getMyOrders(Page<?> page, @Param("memberId") Long memberId);

}
