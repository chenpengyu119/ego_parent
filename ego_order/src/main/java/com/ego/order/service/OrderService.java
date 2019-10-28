package com.ego.order.service;


import com.ego.commons.pojo.OrderParam;
import com.ego.pojo.TbUser;

import java.util.Map;

/**
 * @author pengyu
 * @date 2019/10/11 21:05.
 */
public interface OrderService {

    /**
     * 提交订单
     * @param op
     * @param tbUser 当前用户
     * @return map 包括需要放到作用域中的值：订单号，订单金额等
     */
    Map<String,Object> createOrder(OrderParam op, TbUser tbUser);

}
