package com.tulingxueyuan.mall.modules.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tulingxueyuan.mall.common.api.ResultCode;
import com.tulingxueyuan.mall.common.exception.Asserts;
import com.tulingxueyuan.mall.dto.AddCarDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsCartItem;
import com.tulingxueyuan.mall.modules.oms.mapper.OmsCartItemMapper;
import com.tulingxueyuan.mall.modules.oms.service.OmsCartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mall.modules.pms.model.PmsSkuStock;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductService;
import com.tulingxueyuan.mall.modules.pms.service.PmsSkuStockService;
import com.tulingxueyuan.mall.modules.ums.model.UmsMember;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 */
@Service
public class OmsCartItemServiceImpl extends ServiceImpl<OmsCartItemMapper, OmsCartItem> implements OmsCartItemService {
    @Autowired
    UmsMemberService memberService;
    @Autowired
    PmsSkuStockService skuStockService;
    @Autowired
    PmsProductService productService;
    @Override
    public Boolean add(AddCarDTO addCarDTO) {
        OmsCartItem omsCartItem = new OmsCartItem();
        BeanUtils.copyProperties(addCarDTO,omsCartItem);


        OmsCartItem cartItem = getCartItem(omsCartItem.getProductId(), omsCartItem.getProductSkuId());
        if (cartItem==null) {


            //查询sku
            PmsSkuStock sku = skuStockService.getById(omsCartItem.getProductSkuId());
            if (sku == null) Asserts.fail(ResultCode.VALIDATE_FAILED);
            omsCartItem.setPrice(sku.getPrice());
            omsCartItem.setSp1(sku.getSp1());
            omsCartItem.setSp2(sku.getSp2());
            omsCartItem.setSp3(sku.getSp3());
            omsCartItem.setProductPic(sku.getPic());
            omsCartItem.setProductSkuCode(sku.getSkuCode());
            PmsProduct product = productService.getById(omsCartItem.getProductId());
            if (product == null) Asserts.fail(ResultCode.VALIDATE_FAILED);
            omsCartItem.setProductName(product.getName());
            omsCartItem.setProductSn(product.getProductSn());
            omsCartItem.setProductSubTitle(product.getSubTitle());
            omsCartItem.setProductCategoryId(product.getProductCategoryId());
            omsCartItem.setCreateDate(new Date());
            omsCartItem.setModifyDate(new Date());

           return baseMapper.insert(omsCartItem)>0;
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity()+1);
            omsCartItem.setModifyDate(new Date());
            UpdateWrapper<OmsCartItem> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda()
                    .set(OmsCartItem::getQuantity,cartItem.getQuantity())
                    .set(OmsCartItem::getModifyDate,cartItem.getModifyDate())
                    .eq(OmsCartItem::getId,cartItem.getId());

           return baseMapper.update(cartItem,updateWrapper)>0;
        }

    }
    //初始化购物出数据
    @Override
    public List<OmsCartItem> getList() {
        //当前用户
        QueryWrapper<OmsCartItem> queryWrapper = new QueryWrapper<>();
        UmsMember currentMember = memberService.getCurrentMember();
        //queryWrapper.lambda().eq(OmsCartItem::getMemberId,currentMember.getId());
        return this.list( queryWrapper);

    }
    //更新商品数量
    @Override
    public boolean updateQuantity(Long id, Integer quantity) {
        UpdateWrapper<OmsCartItem> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .set(OmsCartItem::getQuantity,quantity)
                .eq(OmsCartItem::getId,id);
       return this.update(updateWrapper);
    }

    @Override
    public Boolean delete(Long ids) {
        return this.removeById(ids);
    }

    //判断同一个 sku 用户下是否添加了重复购物车
    public OmsCartItem getCartItem(Long productId,Long skuId){
        QueryWrapper<OmsCartItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(OmsCartItem::getProductId,productId)
                .eq(OmsCartItem::getProductSkuId,skuId);

       return baseMapper.selectOne(queryWrapper);

    }
}
