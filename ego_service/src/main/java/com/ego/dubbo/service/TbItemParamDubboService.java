package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbItemParam;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/24 14:02.
 */
public interface TbItemParamDubboService {

    /**
     * 分页查询规格参数信息
     * @param pageNum 当前页码
     * @param pageSize 最大行数
     * @return
     */
    List<TbItemParam> selectByPage(int pageNum,int pageSize);

    /**
     * 根据商品类目查询规格参数
     * @param itemCatId
     * @return
     */
    TbItemParam selectByItemCatId(Long itemCatId);

    /**
     * 新增规格参数
     * @param tbItemParam
     * @return
     */
    int insert(TbItemParam tbItemParam);

    /**
     * 根据主键批量删除规格参数
     * @param ids
     * @return
     */
    int deleteByIds(Long[] ids) throws DaoException;
}
