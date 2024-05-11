package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrderItem;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/***
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="我的订单列表数据传输对象", description="我的订单列表数据传输对象")
public class OrderListDTO extends OmsOrder {
    private List<OmsOrderItem> orderItemList;
}
