package com.tulingxueyuan.mall.modules.ums.service.impl;

import com.tulingxueyuan.mall.modules.ums.model.UmsAdminPermissionRelation;
import com.tulingxueyuan.mall.modules.ums.mapper.UmsAdminPermissionRelationMapper;
import com.tulingxueyuan.mall.modules.ums.service.UmsAdminPermissionRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限) 服务实现类
 * </p>
 */
@Service
public class UmsAdminPermissionRelationServiceImpl extends ServiceImpl<UmsAdminPermissionRelationMapper, UmsAdminPermissionRelation> implements UmsAdminPermissionRelationService {

}
