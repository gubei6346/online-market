package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="商品列表筛选条件",
        description="用于商品列表筛选条件参数接收")
public class ProductConditionDTO {
    private String keyword;
    private Integer pageNum;
    private Integer pageSize;
    private Integer publishStatus;
    private Integer verifyStatus;
    private String productSn;
    private Long productCategoryId;
    private Long brandId;



}
