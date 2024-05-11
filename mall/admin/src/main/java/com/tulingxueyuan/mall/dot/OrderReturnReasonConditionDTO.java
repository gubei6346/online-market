package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "请求退货原因设置列表", description = "请求退货原因设置列表")
public class OrderReturnReasonConditionDTO {

    @ApiModelProperty(value = "页数")
    private Integer pageNum;

    @ApiModelProperty(value = "多少页")
    private Integer pageSize;
}
