package com.ego.dubbo.service;

import com.ego.pojo.TbItemCat;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/23 9:49.
 */
public interface TbItemCatDubboService {
    /**
     * 根据父id查询状态为正常的类目
     * @param pid
     * @return
     */
    List<TbItemCat> selectByPid(Long pid);

    /**
     * 主键查询
     * @param id
     * @return
     */
    TbItemCat getById(Long id);


}
