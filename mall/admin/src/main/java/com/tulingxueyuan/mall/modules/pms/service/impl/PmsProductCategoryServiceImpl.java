package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsProductCategoryMapper;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategoryAttributeRelation;
import com.tulingxueyuan.mall.dto.PmsProductCategoryDTO;
import com.tulingxueyuan.mall.dto.ProductCateChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryAttributeRelationService;
import com.tulingxueyuan.mall.modules.pms.service.PmsProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 */
@Service
public class PmsProductCategoryServiceImpl extends ServiceImpl<PmsProductCategoryMapper, PmsProductCategory> implements PmsProductCategoryService {

    @Autowired
    PmsProductCategoryAttributeRelationService relationService;
    @Autowired
    PmsProductCategoryMapper productCategoryMapper;

    @Override
    public Page list(Long parentId, Integer pageNum, Integer pageSize) {
        Page page=new Page(pageNum,pageSize);
        //条件构造器
        QueryWrapper<PmsProductCategory> queryWrapper=new QueryWrapper<>();
        //queryWrapper.eq("parent_id",parentId);
        queryWrapper.lambda().eq(PmsProductCategory::getParentId,parentId)
        .orderByAsc(PmsProductCategory::getSort);
        return this.page(page,queryWrapper);
    }
//修改导航栏显示状态
    @Override
    public boolean updateNavStatus(List<Long> ids, Integer navStatus) {
    UpdateWrapper<PmsProductCategory> pmsProductCategoryUpdateWrapper=new UpdateWrapper<>();

        pmsProductCategoryUpdateWrapper.lambda()
                //需要更新的列
                .set(PmsProductCategory::getNavStatus,navStatus)
                .in(PmsProductCategory::getId,ids);
        return this.update(pmsProductCategoryUpdateWrapper);
    }
    //修改是否显示按钮状态
    @Override
    public boolean updateShowStatus(List<Long> ids, Integer showStatus) {
        UpdateWrapper<PmsProductCategory> pmsProductCategoryUpdateWrapper=new UpdateWrapper<>();
        pmsProductCategoryUpdateWrapper.lambda()
                //需要更新的列
                .set(PmsProductCategory::getNavStatus,showStatus)
                .in(PmsProductCategory::getId,ids);
        return this.update(pmsProductCategoryUpdateWrapper);

    }
//筛选属性
    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean CustomSave(PmsProductCategoryDTO productCategoryDTO) {
        //保存商品分类
        PmsProductCategory productCategory=new PmsProductCategory();
        //通过BeanUtils将数据传输对象的数据拷贝到productCategory
        BeanUtils.copyProperties(productCategoryDTO,productCategory);
        // 得到分类保存后的主键id, 保存商品分类筛选属性关系
        productCategory.setProductCount(0);
        if(productCategory.getParentId()==0){
            productCategory.setLevel(0);
        }else{
            productCategory.setLevel(1);
        }
        this.save(productCategory);

        saveAttrRelation(productCategoryDTO, productCategory);
        return true;
    }
    //添加关联属性
    private boolean saveAttrRelation(PmsProductCategoryDTO productCategoryDTO, PmsProductCategory productCategory) {
        List<Long> productAttributeListId= productCategoryDTO.getProductAttributeIdList();
        List<PmsProductCategoryAttributeRelation> list=new ArrayList<>();
        for (Long attrId : productAttributeListId) {
            PmsProductCategoryAttributeRelation productCategoryAttributeRelation=new PmsProductCategoryAttributeRelation();
            productCategoryAttributeRelation.setProductCategoryId(productCategory.getId());
            productCategoryAttributeRelation.setProductAttributeId(attrId);
            list.add(productCategoryAttributeRelation);
        }
        return relationService.saveBatch(list);

    }

    @Override
    public boolean update(PmsProductCategoryDTO productCategoryDTO) {
        //保存商品分类
        PmsProductCategory productCategory=new PmsProductCategory();
        //通过BeanUtils将数据传输对象的数据拷贝到productCategory
        BeanUtils.copyProperties(productCategoryDTO,productCategory);
        // 得到分类保存后的主键id, 保存商品分类筛选属性关系
        productCategory.setProductCount(0);
        if(productCategory.getParentId()==0){
            productCategory.setLevel(0);
        }else{
            productCategory.setLevel(1);
        }
        this.updateById(productCategory);
        //删除已保存的关联属性--根据商品分类id删除
        QueryWrapper<PmsProductCategoryAttributeRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PmsProductCategoryAttributeRelation::getProductCategoryId,productCategory.getId());
        relationService.remove(queryWrapper);
        saveAttrRelation(productCategoryDTO, productCategory);
        return true;
    }
    //获取商品一级和二级分类的下拉级联数据
    @Override
    public List<ProductCateChildrenDTO> getWithChildren() {
        return productCategoryMapper.getWithChildren();
    }


}
