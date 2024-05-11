package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="",
        description="")
public class ProductUpdateInitDTO extends ProductSaveParamsDTO{
    //一级分类id
    private  Long cateParentId;
}
