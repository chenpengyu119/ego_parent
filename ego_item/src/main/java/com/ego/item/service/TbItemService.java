package com.ego.item.service;

import com.ego.item.pojo.TbItemChild;

/**
 * @author pengyu
 * @date 2019/10/8 15:18.
 */
public interface TbItemService {

    /**
     * 根据商品id查询商品详细信息
     * @param id
     * @return
     */
    TbItemChild showDetails(Long id);

    /**
     * 根据id查询商品描述信息
     * @param id
     * @return
     */
    String showItemDesc(Long id);

    /**
     * 根据商品id查询商品参数信息
     * @param id
     * @return
     */
    String showItemParamItem(Long id);
}
