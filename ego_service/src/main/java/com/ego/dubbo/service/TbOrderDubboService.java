package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/10/11 21:27.
 */
public interface TbOrderDubboService {

    /**
     * 新增订单
     * @param tbOrder
     * @param tbOrderItemList
     * @param tbOrderShipping
     * @exception
     * @return
     */
    int insertOrder(TbOrder tbOrder, List<TbOrderItem> tbOrderItemList, TbOrderShipping tbOrderShipping) throws Exception;
}
