package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "请求订单列表筛选条件", description = "请求订单列表筛选条件")
public class OrderConditonDTO {
    @ApiModelProperty(value = "页数")
    private Integer pageNum;

    @ApiModelProperty(value = "多少页")
    private Integer pageSize;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "收货人姓名/手机号码")
    private String receiverKeyword;

    @ApiModelProperty(value = "订单状态")
    private Integer status;

    @ApiModelProperty(value = "订单分类")
    private Integer orderType;

    @ApiModelProperty(value = "订单来源")
    private Integer sourceType;

    @ApiModelProperty(value = "提交时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
