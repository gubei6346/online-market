package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="分类和筛选属性关联数据", description="用于筛选属性已保存数据初始化")
public class RelationAttrInfoDTO {
    //商品类型id
    private Long attributeCategoryId;
    //商品属性id
    private Long attributeId;
}
