package com.tulingxueyuan.mall.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "请求退货申请处理列表条件", description = "请求退货申请处理列表条件")
public class OrderReturnApplyConditionDTO {

    @ApiModelProperty(value = "服务单号-订单id")
    private Long id;

    @ApiModelProperty(value = "页数")
    private Integer pageNum;

    @ApiModelProperty(value = "多少页")
    private Integer pageSize;

    @ApiModelProperty(value = "处理状态")
    private Integer status;


    @ApiModelProperty(value = "操作人员")
    private String handleMan;

    @ApiModelProperty(value = "申请时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "处理时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date handleTime;


}
