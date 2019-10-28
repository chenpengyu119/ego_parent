package com.ego.passport.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

/**
 * @author pengyu
 * @date 2019/10/9 16:29.
 */
public interface PassportService {

    /**
     * 验证用户登录
     * @param tbUser
     * @return
     */
    EgoResult login(TbUser tbUser);

    /**
     * 显示登录用户
     * @param token
     * @return
     */
    EgoResult showUser(String token);

    /**
     * 注销
     * @param token
     * @return
     */
    EgoResult logout(String token);

    /**
     * 验证用户名或手机号是否被占用
     * @param param
     * @param type
     * @return
     */
    EgoResult check(String param,int type);

    /**
     * 用户注册
     * @param tbUser
     * @return
     */
    EgoResult register(TbUser tbUser);


}
