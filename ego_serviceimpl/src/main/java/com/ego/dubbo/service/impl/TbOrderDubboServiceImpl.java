package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/10/11 21:30.
 */
@Service
public class TbOrderDubboServiceImpl implements TbOrderDubboService {
    
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Autowired
    private TbItemMapper tbItemMapper;
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOrder(TbOrder tbOrder, List<TbOrderItem> tbOrderItemList, TbOrderShipping tbOrderShipping) throws Exception {

        int indexOrder = tbOrderMapper.insertSelective(tbOrder);
        if (indexOrder==1){
            // 更新订单项表
            int itemIndex = 0;
            for (TbOrderItem item : tbOrderItemList){
                itemIndex += tbOrderItemMapper.insert(item);
            }
            if (itemIndex == tbOrderItemList.size()){
                // 更新派送表
                int indexShip = tbOrderShippingMapper.insertSelective(tbOrderShipping);
                if (indexShip==1){
                    // 更新商品库存
                    int indexUpdateNum = 0;
                    for (TbOrderItem item : tbOrderItemList){
                        // 根据itemId查询当前商品信息
                        TbItem current = tbItemMapper.selectByPrimaryKey(Long.parseLong(item.getItemId()));
                        if (current!=null) {
                            // 库存 当前库存-订单中该商品数量
                            current.setNum(current.getNum() - item.getNum());
                            // 设置更新时间为订单创建时间
                            current.setUpdated(tbOrder.getCreateTime());
                            indexUpdateNum += tbItemMapper.updateByPrimaryKeySelective(current);
                        }
                    }
                    if (indexUpdateNum == tbOrderItemList.size()){
                        return 1;
                    }
                }
            }
        }
        throw new DaoException("创建订单失败");
    }
}
