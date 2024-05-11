package com.tulingxueyuan.mall.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="首页推荐类型及商品列表数据传输对象", description="首页推荐类型及商品列表数据传输对象")

public class HomeGoodsSaleDTO {
    private String categoryName;
    private String pic;
    private String url;
    private List<ProductDTO> productList;
}
