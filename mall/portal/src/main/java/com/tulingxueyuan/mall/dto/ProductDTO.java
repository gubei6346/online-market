package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="商品数据传输对象", description="商品数据传输对象")

public class ProductDTO {
    private Long id;
    private String name;
    private String pic;
    @ApiModelProperty(value = "促销价格")
    private BigDecimal promotionPrice;
    @ApiModelProperty(value = "市场价")
    private BigDecimal originalPrice;
    @ApiModelProperty(value = "副标题")
    private String subTitle;
    private Integer sub;


}
