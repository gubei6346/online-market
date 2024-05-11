package com.tulingxueyuan.mall.modules.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mall.modules.oms.mapper.OmsOrderSettingMapper;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrderSetting;
import com.tulingxueyuan.mall.modules.oms.service.OmsOrderSettingService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单设置表 服务实现类
 * </p>
 *
 */
@Service
public class OmsOrderSettingServiceImpl extends ServiceImpl<OmsOrderSettingMapper, OmsOrderSetting> implements OmsOrderSettingService {


    @Override
    public boolean update(Long id, OmsOrderSetting orderSetting) {
        // 先得查出数据库中是否存在数据，没有save，有update
        orderSetting.setSettingId(id);
        QueryWrapper<OmsOrderSetting> omsOrderSettingQueryWrapper = new QueryWrapper<>();
        omsOrderSettingQueryWrapper.lambda().eq(OmsOrderSetting::getSettingId, orderSetting.getSettingId());
        List<OmsOrderSetting> list = list(omsOrderSettingQueryWrapper);
        if (list.size() > 0) {
            Long ID = list.get(0).getId();
            orderSetting.setId(ID);
            return this.updateById(orderSetting);
        } else {
            return this.save(orderSetting);
        }


    }
}
