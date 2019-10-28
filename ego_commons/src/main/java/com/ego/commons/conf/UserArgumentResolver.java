package com.ego.commons.conf;

import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.ServletUtil;
import com.ego.pojo.TbUser;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 获取登录用户信息
 * @author pengyu
 * @date 2019/10/10 16:19.
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 获取参数类型
        Class<?> clazz = parameter.getParameterType();
        // 如果类型为MiaoShaUser，就会返回true，就会进行下面方法中的处理
        return clazz == TbUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 从cookies中取出用户信息的token
        String token = CookieUtils.getCookieValue(ServletUtil.getRequest(), "TT_TOKEN", true);
        TbUser user =  null;
        if (Strings.isNotBlank(token)){
            // 去redis中将用户信息取出
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbUser>(TbUser.class));
            if (redisTemplate.hasKey(token)){
                user = (TbUser) redisTemplate.opsForValue().get(token);
            }
        }
        return user;
    }
}
