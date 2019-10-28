package com.ego.service;

import com.ego.commons.pojo.EgoResult;

/**
 * @author pengyu
 * @date 2019/9/24 15:31.
 */
public interface TbItemParamItemService {

    /**
     * 根据商品id展示商品规格参数
     * @param itemIde
     * @return
     */
    EgoResult showItemParamByItemId(Long itemIde);
}
