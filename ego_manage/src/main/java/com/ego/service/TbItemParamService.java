package com.ego.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/24 14:08.
 */
public interface TbItemParamService {

    /**
     * 显示规格参数列表
     * @param page 当前页码
     * @param rows 最大行数
     * @return
     */
    List<TbItemParam> showParam(int page, int rows);

    /**
     * 显示对应类目的规格参数
     * @param itemCatId
     * @return
     */
    EgoResult showParamByItemCatId(Long itemCatId);

    /**
     * 新增规格参数
     * @param paramData 规格参数
     * @param itemCatId 商品类目id
     * @return
     */
    EgoResult insert(String paramData,Long itemCatId);

    /**
     * 根据主键批量删除规格参数
     * @param ids
     * @return
     */
    EgoResult deleteByIds(String ids);
}
