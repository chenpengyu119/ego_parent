package com.ego.portal.service;

/**
 * @author pengyu
 * @date 2019/9/28 16:09.
 */
public interface PortalService {

    /**
     * 显示首页大广告
     * 因为需要作用域传值，且前端需要字符串，所以直接返回Json字符串
     * @return
     */
    String showPortalAd();

}
