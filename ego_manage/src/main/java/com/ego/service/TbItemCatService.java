package com.ego.service;

import com.ego.commons.pojo.EasyUITreeNode;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/23 15:32.
 */
public interface TbItemCatService {
    /**
     * 根据id查询商品类目
     * @param id
     * @return
     */
    List<EasyUITreeNode> selectById(Long id);
}
