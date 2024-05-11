package com.tulingxueyuan.mall.modules.sms.service;

import com.tulingxueyuan.mall.modules.sms.model.SmsHomeAdvertise;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页轮播广告表 服务类
 * </p>
 *
 */
public interface SmsHomeAdvertiseService extends IService<SmsHomeAdvertise> {

    List<SmsHomeAdvertise> getHomeBanners();
}
