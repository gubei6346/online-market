package com.tulingxueyuan.mall.modules.oms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.OrderReturnApplyConditionDTO;
import com.tulingxueyuan.mall.modules.oms.mapper.OmsOrderReturnApplyMapper;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrderReturnApply;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderReturnApplyService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单退货申请 服务实现类
 * </p>
 *
 */
@Service
public class OmsOrderReturnApplyServiceImpl extends ServiceImpl<OmsOrderReturnApplyMapper, OmsOrderReturnApply> implements OmsOrderReturnApplyService {

    @Override
    public Page list(OrderReturnApplyConditionDTO condition) {
        Page page = new Page(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<OmsOrderReturnApply> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OmsOrderReturnApply> lambdaWrapper = queryWrapper.lambda();
        // 服务单号
        if (condition.getId() != null) {
            lambdaWrapper.eq(OmsOrderReturnApply::getOrderId, condition.getId());
        }
        // 申请时间
        if (condition.getCreateTime() != null) {
            lambdaWrapper.ge(OmsOrderReturnApply::getCreateTime, condition.getCreateTime()).le(OmsOrderReturnApply::getCreateTime, new Date(condition.getCreateTime().getTime() + 86400000));
        }
        // 处理时间
        if (condition.getHandleTime() != null) {
            lambdaWrapper.ge(OmsOrderReturnApply::getHandleTime, condition.getHandleTime()).le(OmsOrderReturnApply::getHandleTime, new Date(condition.getHandleTime().getTime() + 86400000));
        }
        // 处理状态
        if (condition.getStatus() != null) {
            lambdaWrapper.eq(OmsOrderReturnApply::getStatus, condition.getStatus());
        }
        // 操作人
        if (!StrUtil.isBlank(condition.getHandleMan())) {
            lambdaWrapper.eq(OmsOrderReturnApply::getHandleMan, condition.getHandleMan());
        }

        return this.page(page, lambdaWrapper);
    }

    @Override
    public boolean delete(List<Long> ids) {
        return this.baseMapper.deleteBatchIds(ids) == ids.size();
    }
}
