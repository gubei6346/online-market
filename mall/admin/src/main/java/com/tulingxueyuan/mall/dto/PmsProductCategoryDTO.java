package com.tulingxueyuan.mall.dto;

import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="1", description="1")
public class PmsProductCategoryDTO extends PmsProductCategory {
    private List<Long> productAttributeIdList;

}
