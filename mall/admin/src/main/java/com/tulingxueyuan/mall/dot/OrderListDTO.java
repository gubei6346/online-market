package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "订单列表", description = "订单列表")
public class OrderListDTO {
    @ApiModelProperty(value = "订单列表")
    private List<OmsOrder> list;

    @ApiModelProperty(value = "总数")
    private String total;
}
