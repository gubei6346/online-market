package com.tulingxueyuan.mall.modules.ums.service;

import com.tulingxueyuan.mall.modules.ums.model.UmsAdmin;
import com.tulingxueyuan.mall.modules.ums.model.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 */
public interface UmsMemberService extends IService<UmsMember> {

    UmsMember register(UmsMember umsMemberParam);

    UmsMember login(String username, String password);

    UmsMember getAdminByUsername(String username);


    //获取当前登录用户
    UmsMember getCurrentMember();
}
