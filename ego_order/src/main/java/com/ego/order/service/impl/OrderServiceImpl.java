package com.ego.order.service.impl;

import com.ego.commons.pojo.OrderParam;
import com.ego.commons.utils.CartConst;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.ServletUtil;
import com.ego.order.service.OrderService;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.pojo.TbUser;
import com.ego.rabbitmq.send.Sender;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author pengyu
 * @date 2019/10/11 21:06.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private Sender sender;

    @Override
    public Map<String,Object> createOrder(OrderParam op, TbUser user) {

        // 构造数据
        Date now = new Date();
        // 创建订单类对象
        TbOrder tbOrder = new TbOrder();
        tbOrder.setPayment(op.getPayment());
        tbOrder.setPaymentType(op.getPaymentType());
        tbOrder.setUpdateTime(now);
        tbOrder.setCreateTime(now);
        // 设置订单状态为未付款
        tbOrder.setStatus(1);
        // 设置用户编号
        tbOrder.setUserId(user.getId());

        // 订单id
        List<TbOrderItem> orderItems = op.getOrderItems();
        // 生成id
        String orderId = IDUtils.genItemId()+"";
        // 设置所有商品的订单号
        for (TbOrderItem item : orderItems){
            // 生成订单项id
            String id = IDUtils.genItemId()+"";
            item.setOrderId(orderId);
            item.setId(id);
        }
        // 设置订单id
        tbOrder.setOrderId(orderId);
        // 设置shipping订单id
        TbOrderShipping orderShipping = op.getOrderShipping();
        orderShipping.setCreated(now);
        orderShipping.setUpdated(now);
        orderShipping.setOrderId(orderId);

        // 设置订单对象
        op.setTbOrder(tbOrder);

        // 将token携带
        op.setToken(CartConst.CART_REDIS_KEY_PREFIX+user.getId());

        // 发送创建订单的消息
        sender.sendOrderCreate(op);

        Map<String,Object> res = new HashMap<>();

        // 返回订单号
        res.put("orderId", orderId);
        // 返回订单金额
        res.put("payment", tbOrder.getPayment());

        return res;
    }
}
