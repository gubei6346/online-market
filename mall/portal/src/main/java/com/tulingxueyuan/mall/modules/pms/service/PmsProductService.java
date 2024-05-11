package com.tulingxueyuan.mall.modules.pms.service;

import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.dto.ProductDetailDTO;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 */
public interface PmsProductService extends IService<PmsProduct> {

    ProductDetailDTO getProductDetail(Long id);
}
