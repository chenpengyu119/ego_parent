package com.ego.passport.service.impl;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.ServletUtil;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.PassportService;
import com.ego.pojo.TbUser;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sun.security.provider.MD5;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

/**
 * @author pengyu
 * @date 2019/10/9 16:30.
 */
@Service
public class PassportServiceImpl implements PassportService {

    @Reference
    private TbUserDubboService tbUserDubboService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public EgoResult login(TbUser tbUser) {
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        TbUser user = tbUserDubboService.selectByTbUser(tbUser);
        if (user!=null){
            // 将用户数据放到redis中
            String token = UUID.randomUUID().toString();
            // 去除用户数据中的密码
            user.setPassword("");
            // 有效时间
            long timeout = 2*7*24*60*60L;
            redisTemplate.opsForValue().set(token,user, Duration.ofSeconds(timeout));
            // 将token放到cookies中
            CookieUtils.setCookie(ServletUtil.getRequest(), ServletUtil.getResponse()
            // cookiesName是在前台写死的 最大有效时间设置为两周,单位是秒
                    , "TT_TOKEN", token, (int)timeout);
            return EgoResult.ok();
        }
        return EgoResult.error("登录失败");
    }

    @Override
    public EgoResult showUser(String token) {

        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
        TbUser user = (TbUser) redisTemplate.opsForValue().get(token);
        if (user!=null) {
            return EgoResult.ok(user);
        }else {
            return EgoResult.error("失败");
        }
    }

    @Override
    public EgoResult logout(String token) {
        //删除redis数据
        if (redisTemplate.hasKey(token)){
            boolean index = redisTemplate.delete(token);
            if (index){
                //删除cookies数据
                CookieUtils.deleteCookie(ServletUtil.getRequest(), ServletUtil.getResponse(), "TT_TOKEN");
                return EgoResult.ok();
            }
        }
        return EgoResult.error("注销失败");
    }

    @Override
    public EgoResult check(String param, int type) {
        TbUser user = new TbUser();
        if (type==1){
            // 验证用户名占用
            user.setUsername(param);
        }else if (type==2){
            // 验证手机号占用
            user.setPhone(param);
        }
        int index = tbUserDubboService.selectCountByTbUser(user);
        if (index==0){
            // 数据可用
            return EgoResult.ok(true);
        }
        // 数据不可用
        return EgoResult.ok(false);
    }

    @Override
    public EgoResult register(TbUser tbUser) {

        Date now = new Date();
        // 创建时间
        tbUser.setCreated(now);
        // 更新时间
        tbUser.setUpdated(now);
        // 加密密码
        if (tbUser.getPassword()!=null) {
            tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
        }
        int index = tbUserDubboService.insert(tbUser);
        return index==1?EgoResult.ok():EgoResult.error("注册失败");
    }


}
