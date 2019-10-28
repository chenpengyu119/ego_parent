package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/10/9 16:33.
 */
@Service
public class TbUserDubboServiceImpl implements TbUserDubboService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser selectByTbUser(TbUser tbUser) {

        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(tbUser.getUsername())
                .andPasswordEqualTo(tbUser.getPassword());
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        if (tbUsers!=null && tbUsers.size()==1){
            return tbUsers.get(0);
        }
        return null;
    }

    @Override
    public int selectCountByTbUser(TbUser tbUser) {

        TbUserExample example = new TbUserExample();
        // 动态sql
        if (tbUser.getUsername()!=null&&!"".equals(tbUser.getUsername())){
            // 查询用户名是否已存在
            example.createCriteria().andUsernameEqualTo(tbUser.getUsername());
        }
        if (tbUser.getPhone()!=null&&!"".equals(tbUser.getPhone())){
            // 查询手机号是否存在
            example.createCriteria().andPhoneEqualTo(tbUser.getPhone());
        }
        long index = tbUserMapper.countByExample(example);
        if (index>0){
            // 手机号已被存在返回1
            return 1;
        }
        return 0;
    }

    @Override
    public int insert(TbUser tbUser) {
        return tbUserMapper.insertSelective(tbUser);
    }
}
