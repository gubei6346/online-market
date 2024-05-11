package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductAttributeMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.tulingxueyuan.mall.dto.RelationAttrInfoDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeCategoryService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductAttributeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务实现类
 * </p>
 *
 */
@Service
public class PmsProductAttributeServiceImpl extends ServiceImpl<PmsProductAttributeMapper, PmsProductAttribute> implements PmsProductAttributeService {
    @Autowired
    PmsProductAttributeMapper productAttributeMapper;
    @Autowired
    PmsProductAttributeCategoryService productAttributeCategoryService;
    @Override
    public Page list(Long cid,Integer type, Integer pageNum, Integer pageSize) {
        Page page=new Page(pageNum,pageSize);
        QueryWrapper<PmsProductAttribute>queryWrapper=new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(PmsProductAttribute::getProductAttributeCategoryId,cid)
                .eq(PmsProductAttribute::getType,type)
                .orderByAsc(PmsProductAttribute::getSort);

        return  this.page(page,queryWrapper);

    }

    @Override
    public boolean create(PmsProductAttribute productAttribute) {
        //保存商品属性
        boolean save=this.save(productAttribute);
        if(save){
            //更新对应参数数量
            //先查询商品类型 再更新||直接更新
            UpdateWrapper<PmsProductAttributeCategory>updateWrapper = new UpdateWrapper<>();
            //属性
            if(productAttribute.getType()==0){
                updateWrapper.setSql("attribute_count=attribute_count+1");
            }
            //参数
            else if(productAttribute.getType()==1){
                updateWrapper.setSql("param_count=param_count+1");
            }
            updateWrapper.lambda().eq(PmsProductAttributeCategory::getId, productAttribute.getProductAttributeCategoryId());
            productAttributeCategoryService.update(updateWrapper);
        }
        return save;
    }

    @Override
    public List<RelationAttrInfoDTO> getRelationAttrInfoByCid(Long cId) {
        return productAttributeMapper.getRelationAttrInfoByCid(cId);
    }
}
