package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.oms.model.OmsCartItem;
import com.tulingxueyuan.mall.modules.ums.model.UmsMemberReceiveAddress;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "确认订单页的数据传输",description = "确认订单页的数据传输")
public class ConfirmOrderDTo {
    private List<OmsCartItem> cartList;
    private Integer productTotal;
    private BigDecimal priceTotal;
    private BigDecimal freightAmount;
    private BigDecimal payAmount;
    private List<UmsMemberReceiveAddress> addressList;
}
