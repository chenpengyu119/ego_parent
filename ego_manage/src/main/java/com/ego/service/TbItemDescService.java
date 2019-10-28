package com.ego.service;

import com.ego.pojo.TbItemDesc;

/**
 * @author pengyu
 * @date 2019/9/23 17:11.
 */
public interface TbItemDescService {

    /**
     * 根据id查询商品描述
     * @param id
     * @return
     */
    TbItemDesc getById(Long id);
}
