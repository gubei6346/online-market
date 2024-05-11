package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeValue;
import com.tulingxueyuan.mall.modules.pms.model.PmsSkuStock;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProductConditionDTO商品保存数据传输对象",
        description="用于商品的添加、修改保存的参数接收")
public class ProductSaveParamsDTO extends PmsProduct {

    // 商品属性相关
    @ApiModelProperty(value = "商品属性相关")
    private List<PmsProductAttributeValue> productAttributeValueList;
    // 商品sku库存信息
    @ApiModelProperty(value = "商品sku库存信息")
    @Size(min = 0,message = "")
    @Valid   // 嵌套验证支持
    private List<PmsSkuStock> skuStockList;
}
