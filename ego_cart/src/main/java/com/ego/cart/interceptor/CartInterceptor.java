package com.ego.cart.interceptor;

import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.ServletUtil;
import com.ego.pojo.TbUser;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户未登录时不允许进行结算
 * @author pengyu
 * @date 2019/10/11 16:51.
 */
@Component
public class CartInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Value("${custom.cart.loginUrl}")
    private String loginUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 取出用户信息
        // 从cookies中取出用户信息的token
        String token = CookieUtils.getCookieValue(ServletUtil.getRequest(), "TT_TOKEN", true);
        TbUser user =  null;
        if (Strings.isNotBlank(token)){
            // 去redis中将用户信息取出
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
            if (redisTemplate.hasKey(token)){
                user = (TbUser) redisTemplate.opsForValue().get(token);
            }
        }else {
            user = null;
        }

        // 判断是否登录
        if (user == null){
            // 未登录，跳转到登录页面
            response.sendRedirect(loginUrl);
            return false;
        }else {
            // 已登录
            return true;
        }

    }

}
