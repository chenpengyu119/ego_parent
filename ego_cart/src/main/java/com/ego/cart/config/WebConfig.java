package com.ego.cart.config;

import com.ego.cart.interceptor.CartInterceptor;
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
public class WebConfig implements WebMvcConfigurer {

    /**
     * 拦截器 拦截未登录用户进行结算
     */
    @Autowired
    private CartInterceptor cartInterceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cartInterceptor).addPathPatterns("/cart/order-cart.html");
    }


}
