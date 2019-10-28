package com.ego.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

/**
 * @author pengyu
 * @date 2019/9/20 16:58.
 */
public interface TbItemService {

    /**
     * 展示商品详情
     * @param page 当前页码
     * @param rows 页面最大显示行数
     * @return
     */
    EasyUIDataGrid showItem(int page,int rows);

    /**
     * 根据id更新商品状态
     * @param statusStr 商品状态  instock 下架; reshelf:上架 ;  delete : 删除
     * @param idsStr 商品id
     * @return
     */
    EgoResult updateStatusByIds(String  statusStr,String idsStr);

    /**
     * 新增商品信息和商品描述
     * @param tbItem
     * @param desc 前台传过来名字的是desc
     * @return
     */
    int insertItem(TbItem tbItem, String desc, String itemParams) throws Exception;

    /**
     * 更新商品信息
     * @param tbItem
     * @param desc
     * @return
     * @throws Exception
     */
    int updateItem(TbItem tbItem, String desc,String itemParams,Long itemParamId) throws Exception;
}
