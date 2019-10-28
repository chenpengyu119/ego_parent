package com.ego.commons.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 *  相当于配置文件
 * @author pengyu
 * @date 2019/10/10 16:18.
 */
@Configuration
public class WebConfigCommons implements WebMvcConfigurer {

    /**
     * 参数解析器，用于注入用户实体
     */
    @Autowired
    private UserArgumentResolver userArgumentResolver;



    /**
     * 注册登录用户参数解析器
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 添加一个专门解析TbUser类型参数的解析器
        argumentResolvers.add(userArgumentResolver);
    }


}
