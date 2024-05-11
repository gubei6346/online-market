package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/***
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="下单传输传输对象", description="下单传输传输对象")
public class OrderParamDTO {

    @ApiModelProperty("购物车id， 根据购物下单")
    private List<Long> itemIds;

    @ApiModelProperty("地址id")
    private Long memberReceiveAddressId;


    @ApiModelProperty("商品id,根据立即购买下单")
    private List<Long> productId;

    @ApiModelProperty("skuId,根据立即购买下单")
    private List<Long> skuId;

}
