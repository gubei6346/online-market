package com.tulingxueyuan.mall.modules.ums.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.db.Session;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tulingxueyuan.mall.common.exception.Asserts;
import com.tulingxueyuan.mall.common.util.ComConstants;
import com.tulingxueyuan.mall.modules.ums.model.UmsMember;
import com.tulingxueyuan.mall.modules.ums.mapper.UmsMemberMapper;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberCacheService;
import com.tulingxueyuan.mall.modules.ums.service.UmsMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Autowired
    UmsMemberCacheService memberCacheService;
    @Autowired
    HttpSession session;
    @Override
    public  UmsMember getAdminByUsername(String username){
        UmsMember user =memberCacheService.getUser(username);
        if (user!=null) return user;
        QueryWrapper<UmsMember> wrapper=new QueryWrapper<>();
            wrapper.lambda().eq(UmsMember::getUsername,username);
            List<UmsMember> adminList=list(wrapper);
            if (adminList!=null&&adminList.size()>0){
                user=adminList.get(0);
                memberCacheService.setUser(user);
                return user;
            }


        return  null;
    }
    @Override
    public UmsMember register(UmsMember umsMemberParam) {
        UmsMember umsMember=new UmsMember();
        BeanUtils.copyProperties(umsMemberParam,umsMember);
        umsMember.setCreateTime(new Date());
        umsMember.setStatus(1);

        QueryWrapper<UmsMember> wrapper= new QueryWrapper<>();
        wrapper.lambda().eq(UmsMember::getUsername,umsMember.getUsername());
        List<UmsMember> umsMemberList=list(wrapper);
        if (umsMemberList.size()>0){
            return  null;
        }
        String encodePassword= BCrypt.hashpw(umsMember.getPassword());
        umsMember.setPassword(encodePassword);
        baseMapper.insert(umsMember);
        return umsMember;
    }

    @Override
    public UmsMember login(String username, String password) {
        UmsMember umsMember=null;
        try {
            umsMember=getAdminByUsername(username);
            if (!BCrypt.checkpw(password,umsMember.getPassword())){
                Asserts.fail("密码不正确");
            }
        }catch (Exception e){
            Asserts.fail("登录异常"+e.getMessage());
        }
        return  umsMember;
    }
    public UmsMember getCurrentMember(){
        UmsMember umsMember  = (UmsMember) session.getAttribute(ComConstants.FLAG_CURRENT_USER);
        return umsMember;
    }
}
