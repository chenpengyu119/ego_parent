package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

/**
 * @author pengyu
 * @date 2019/9/23 17:03.
 */
public interface TbItemDescDubboService {

    /**
     * 根据主键查询商品描述
     * @param id
     * @return
     */
    TbItemDesc selectById(Long id);
}
