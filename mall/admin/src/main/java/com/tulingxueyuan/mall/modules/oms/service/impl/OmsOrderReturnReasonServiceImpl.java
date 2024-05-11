package com.tulingxueyuan.mall.modules.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.dto.OrderReturnReasonAddConditionDTO;
import com.tulingxueyuan.mall.dto.OrderReturnReasonConditionDTO;
import com.tulingxueyuan.mall.modules.oms.mapper.OmsOrderReturnReasonMapper;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrderReturnReason;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderReturnReasonService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 退货原因表 服务实现类
 * </p>
 *
 */
@Service
public class OmsOrderReturnReasonServiceImpl extends ServiceImpl<OmsOrderReturnReasonMapper, OmsOrderReturnReason> implements OmsOrderReturnReasonService {

    @Override
    public Page list(OrderReturnReasonConditionDTO condition) {
        Page page = new Page(condition.getPageNum(), condition.getPageSize());
        QueryWrapper<OmsOrderReturnReason> omsOrderReturnReasonQueryWrapper = new QueryWrapper<>();
        omsOrderReturnReasonQueryWrapper.lambda().orderByDesc(OmsOrderReturnReason::getSort);

        return this.page(page, omsOrderReturnReasonQueryWrapper);
    }

    @Override
    public boolean add(OrderReturnReasonAddConditionDTO condition) {
        if (condition.getCreateTime() == null) {
            condition.setCreateTime(new Date());
        }
        OmsOrderReturnReason orderReturnReason = new OmsOrderReturnReason();
        BeanUtils.copyProperties(condition, orderReturnReason);
        return this.save(orderReturnReason);
    }

    @Override
    public boolean updateStatus(int status, Long ids) {
        UpdateWrapper<OmsOrderReturnReason> omsOrderReturnReasonQueryWrapper = new UpdateWrapper<>();
        omsOrderReturnReasonQueryWrapper.lambda().set(OmsOrderReturnReason::getStatus, status).in(OmsOrderReturnReason::getId, ids);
        return this.update(omsOrderReturnReasonQueryWrapper);
    }

    @Override
    public boolean delete(List<Long> ids) {
        boolean result = this.removeByIds(ids);
        if (result) {
            return true;
        } else {
            return false;
        }
    }
}
