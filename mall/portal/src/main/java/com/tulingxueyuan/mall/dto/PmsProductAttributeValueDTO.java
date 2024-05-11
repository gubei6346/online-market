package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeValue;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SPU的数据传输对象", description="用于商品详情展示")
public class PmsProductAttributeValueDTO extends PmsProductAttributeValue {
    private String attrName;

}
