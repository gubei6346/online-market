package com.tulingxueyuan.mall.modules.ums.service.impl;

import com.tulingxueyuan.mall.modules.ums.model.UmsMemberReceiveAddress;
import com.tulingxueyuan.mall.modules.ums.mapper.UmsMemberReceiveAddressMapper;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberReceiveAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员收货地址表 服务实现类
 * </p>
 *
 */
@Service
public class UmsMemberReceiveAddressServiceImpl extends ServiceImpl<UmsMemberReceiveAddressMapper, UmsMemberReceiveAddress> implements UmsMemberReceiveAddressService {

    @Override
    public Boolean add(UmsMemberReceiveAddress address) {
        return  this.save(address);
    }

    @Override
    public Boolean edit(UmsMemberReceiveAddress address) {
        return this.updateById(address);
    }

    @Override
    public Boolean delete(Long id) {
        return this.removeById(id);
    }
}
