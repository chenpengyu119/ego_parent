package com.ego.commons.utils;

/**
 * 购物车模块常量类
 * @author pengyu
 * @date 2019/10/10 16:52.
 */
public class CartConst {
    /**
     * 临时购物车在cookies中存放的key名
     */
    public static final String TMP_CART_COOKIES_KEY = "tempcart";

    /**
     * 购物车存到redis中的key前缀
     */
    public static final String CART_REDIS_KEY_PREFIX = "cart:";

    /**
     * cookies有效时间， 单位：秒
     */
    public static final int COOKIES_TIMEOUT_SECOND = 2*7*24*60*60;
}
