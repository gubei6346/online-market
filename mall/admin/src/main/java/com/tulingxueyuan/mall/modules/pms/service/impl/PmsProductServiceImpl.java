package com.tulingxueyuan.mall.modules.pms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductMapper;
import com.tulingxueyuan.mall.dto.ProductConditionDTO;
import com.tulingxueyuan.mall.dto.ProductSaveParamsDTO;
import com.tulingxueyuan.mall.dto.ProductUpdateInitDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeValueService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {
    @Autowired
    PmsSkuStockService skuStockService;
    @Autowired
    PmsProductAttributeValueService productAttributeValueService;
    @Autowired
    PmsProductMapper productMapper;

    @Override
    public Page list(ProductConditionDTO condition) {

        Page page=new Page(condition.getPageNum(),condition.getPageSize());

        QueryWrapper<PmsProduct> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PmsProduct> lambdaWrapper = queryWrapper.lambda();
        // 商品名称
        if(!StrUtil.isBlank(condition.getKeyword())){
            lambdaWrapper.like(PmsProduct::getName,condition.getKeyword());
        }
        // 商品货号
        if(!StrUtil.isBlank(condition.getProductSn())){
            lambdaWrapper.eq(PmsProduct::getProductSn,condition.getProductSn());
        }

        // 商品分类
        if(condition.getProductCategoryId()!=null && condition.getProductCategoryId()>0){
            lambdaWrapper.eq(PmsProduct::getProductCategoryId,condition.getProductCategoryId());
        }
        // 商品品牌
        if(condition.getBrandId()!=null && condition.getBrandId()>0){
            lambdaWrapper.eq(PmsProduct::getBrandId,condition.getBrandId());
        }
        // 上架状态
        if(condition.getPublishStatus()!=null){
            lambdaWrapper.eq(PmsProduct::getPublishStatus,condition.getPublishStatus());
        }
        // 审核状态
        if(condition.getVerifyStatus()!=null){
            lambdaWrapper.eq(PmsProduct::getVerifyStatus,condition.getVerifyStatus());
        }
        lambdaWrapper.orderByAsc(PmsProduct::getSort);
        return this.page(page,lambdaWrapper);

    }

    @Override
    public boolean updateStatus(Integer status, List<Long> ids, SFunction<PmsProduct, ?> getPublishStatus) {
        UpdateWrapper<PmsProduct> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(getPublishStatus,status)
        .in(PmsProduct::getId,ids);
        return this.update(updateWrapper);
    }

     // 添加

    @Override
    @Transactional
    public boolean create(ProductSaveParamsDTO productSaveParamsDTO) {
        //1，保存商品基本信息
        PmsProduct product=productSaveParamsDTO;
        product.setId(null);
        boolean result=this.save(product);
        if(result){
            //  sku
            SaveManyList(productSaveParamsDTO.getSkuStockList(),product.getId(), skuStockService);

            //  spu
            SaveManyList(productSaveParamsDTO.getProductAttributeValueList(),product.getId(), productAttributeValueService);

        }
        return result;
    }

    @Override
    public ProductUpdateInitDTO getUpdateInfo(Long id) {
        return productMapper.getUpdateInfo(id);
    }

    public void SaveManyList(List list, Long productId, IService service){
        // 如果数据为空 或者长度为0  不做任何操作
        if(CollectionUtil.isEmpty(list)) return;

        try {
            // 循环 反射 赋值商品id
            for (Object obj : list) {
                Method setProductIdMethod = obj.getClass().getMethod("setProductId", Long.class);

                // 在修改状态清除主键id
                Method setId = obj.getClass().getMethod("setId", Long.class);
                setId.invoke(obj,(Long)null);

                // 调用setProductId
                setProductIdMethod.invoke(obj, productId);
            }

            service.saveBatch(list);
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
    //修改保存
    @Override
    @Transactional
    public boolean update(ProductSaveParamsDTO productSaveParamsDTO) {
        // 1. 保存商品基本信息 --商品主表
        PmsProduct product=productSaveParamsDTO;
        boolean result = this.updateById(product);
        if(result) {

            // 根据商品id删除
            DeleteManyListByProductId(product.getId(),skuStockService);
            //  sku
            SaveManyList(productSaveParamsDTO.getSkuStockList(),product.getId(), skuStockService);

            // 根据商品id删除
            DeleteManyListByProductId(product.getId(),productAttributeValueService);
            //  spu
            SaveManyList(productSaveParamsDTO.getProductAttributeValueList(),product.getId(), productAttributeValueService);

        }
        return result;
    }

     //根据商品id删除关联数据

    public void DeleteManyListByProductId(Long productId, IService service){

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id",productId);

        service.remove(queryWrapper);
    }

}
