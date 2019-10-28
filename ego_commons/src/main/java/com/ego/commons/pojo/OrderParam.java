package com.ego.commons.pojo;

import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * 用来接收前端提交的订单数据，包含三个表中的数据：
 *    tb_oder,
 *    tb_order_item,
 *    tb_order_shipping
 * @author pengyu
 * @date 2019/10/11 20:49.
 */
public class OrderParam implements Serializable {

    /**
     * 在表tb_oder中
     */
    private Integer paymentType;

    private String payment;

    /**
     * 和前端提交的数据中名称一致，而且正好里面的内容和该实体一一对应，对应表 tb_order_shipping
     */
    private TbOrderShipping orderShipping;

    /**
     * 对应前端传递的orderItems[i]，对应表tb_order_item
     */
    private List<TbOrderItem> orderItems;

    /**
     * 对应订单表
     */
    private TbOrder tbOrder;

    /**
     * redis中存储用户信息的token
     */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "OrderParam{" +
                "paymentType=" + paymentType +
                ", payment='" + payment + '\'' +
                ", orderShipping=" + orderShipping +
                ", orderItems=" + orderItems +
                ", tbOrder=" + tbOrder +
                ", token='" + token + '\'' +
                '}';
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrder getTbOrder() {
        return tbOrder;
    }

    public void setTbOrder(TbOrder tbOrder) {
        this.tbOrder = tbOrder;
    }
}
