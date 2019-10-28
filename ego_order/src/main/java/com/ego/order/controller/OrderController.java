package com.ego.order.controller;

import com.ego.commons.pojo.OrderParam;
import com.ego.order.service.OrderService;
import com.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author pengyu
 * @date 2019/10/11 20:37.
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @param orderParam
     * @return
     */
    @RequestMapping("/order/create.html")
    public String create(OrderParam orderParam, TbUser user, Model model){
        Map<String, Object> map = orderService.createOrder(orderParam, user);
        model.addAllAttributes(map);
        return "success";
    }
}
