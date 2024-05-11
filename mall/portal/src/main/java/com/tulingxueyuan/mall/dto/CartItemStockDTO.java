package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.oms.model.OmsCartItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/***
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="购物车和库存的数据传输对象", description="购物车和库存的数据传输对象")
public class CartItemStockDTO extends OmsCartItem {
    @ApiModelProperty("真实库存（实际库存-锁定库存）")
    private Integer stock;
}
