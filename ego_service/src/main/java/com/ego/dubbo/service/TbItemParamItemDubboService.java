package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

/**
 * @author pengyu
 * @date 2019/9/24 15:24.
 */
public interface TbItemParamItemDubboService {

    /**
     * 根据商品id查询规格商品规格参数信息
     * @param itemId
     * @return
     */
    TbItemParamItem selectByItemId(Long itemId);
}
