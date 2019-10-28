package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

/**
 * @author pengyu
 * @date 2019/10/9 16:32.
 */
public interface TbUserDubboService {

    /**
     * 根据用户查询用户信息
     * @param tbUser
     * @return
     */
    TbUser selectByTbUser(TbUser tbUser);

    /**
     * 根据用户信息查询用户是否存在
     * @param tbUser
     * @return
     */
    int selectCountByTbUser(TbUser tbUser);

    /**
     * 添加用户
     * @param tbUser
     * @return
     */
    int insert(TbUser tbUser);
}
