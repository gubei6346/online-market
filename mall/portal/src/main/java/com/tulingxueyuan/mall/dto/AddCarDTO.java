package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "添加购物车参数接收对象",description = "")
public class AddCarDTO {
    private Long productId;
    private Long productSkuId;
    private Integer quantity;
}
