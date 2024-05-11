package com.tulingxueyuan.mall.modules.oms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.OrderConditonDTO;
import com.tulingxueyuan.mall.modules.oms.mapper.OmsOrderMapper;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {

    @Override
    public Page list(OrderConditonDTO condition) {
        Page page = new Page(condition.getPageNum(), condition.getPageSize());

        QueryWrapper<OmsOrder> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OmsOrder> lambdaWrapper = queryWrapper.lambda();
        // 订单编号
        if (!StrUtil.isBlank(condition.getOrderSn())) {
            lambdaWrapper.like(OmsOrder::getOrderSn, condition.getOrderSn());
        }
        // 收货人姓名/手机号码
        if (!StrUtil.isBlank(condition.getReceiverKeyword())) {
            lambdaWrapper.eq(OmsOrder::getReceiverName, condition.getReceiverKeyword());
            lambdaWrapper.eq(OmsOrder::getReceiverPhone, condition.getReceiverKeyword());
        }
        // 提交时间
        if (condition.getCreateTime() != null) {
            lambdaWrapper.ge(OmsOrder::getCreateTime, condition.getCreateTime()).le(OmsOrder::getCreateTime, new Date(condition.getCreateTime().getTime() + 86400000));
        }
        // 订单状态
        if (condition.getStatus() != null) {
            lambdaWrapper.eq(OmsOrder::getStatus, condition.getStatus());
        }
        //  订单分类
        if (condition.getOrderType() != null) {
            lambdaWrapper.eq(OmsOrder::getOrderType, condition.getOrderType());
        }
        // 订单来源
        if (condition.getSourceType() != null) {
            lambdaWrapper.eq(OmsOrder::getSourceType, condition.getSourceType());
        }
        return this.page(page, lambdaWrapper);
    }

    @Override
    public boolean delete(List<Long> ids) {
        return this.baseMapper.deleteBatchIds(ids) == ids.size();
    }
}
