package com.ego.dubbo.service;

import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/20 16:36.
 */
public interface TbItemDubboService {

    /**
     * 分页查询商品详情信息
     * @param page 当前页码
     * @param rows 页面最大显示行数
     * @return
     */
    List<TbItem> selectByPage(int page,int rows);

    /**
     * 查询总记录数
     * @return
     */
    long selectCount();

    /**
     * 根据id更新商品状态
     * @param status
     * @param ids
     * @return
     */
    int updateStatusByIds(byte status,long[] ids);

    /**
     * 新增一件商品以及商品描述
     * @param tbItem  商品
     * @param itemDesc 商品描述
     * @return
     */
    int insertItem(TbItem tbItem, TbItemDesc itemDesc, TbItemParamItem tbItemParamItem) throws Exception;

    /**
     * 更新商品和描述信息
     * @param tbItem 商品信息
     * @param tbItemDesc 商品描述
     * @param tbItemParamItem 商品规格参数
     * @return
     */
    int updateItemByItem(TbItem tbItem,TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) throws Exception;

    /**
     * 查询全部
     * @return
     */
    List<TbItem> selectList();

    /**
     * 主键查询
     * @param id
     * @return
     */
    TbItem getById(Long id);
}
