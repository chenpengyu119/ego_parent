package com.ego.cart.controller;

import com.ego.cart.pojo.CartPojo;
import com.ego.cart.serivce.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/10/10 16:10.
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     *
     * @param id 商品id
     * @param num 加购数量
     * @param user 注入用户信息，判断当前是否登录，null表示未登录
     * @return
     */
    @RequestMapping("/cart/add/{id}.html")
    @CrossOrigin
    public String  addCart(@PathVariable Long id, int num, TbUser user){
       cartService.addCart(id, num, user);
       return "cartSuccess";
    }

    /**
     * 显示购物车页面
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/cart/cart.html")
    public String showCart(Model model,TbUser user){
        model.addAttribute("cartList", cartService.showCart(user));
        return "cart";
    }

    /**
     * 合并购物车，并跳回之前页面
     * @param url
     * @return
     */
    @RequestMapping("/cart/merge")
    public String merge(String url,TbUser tbUser){
        cartService.merge(tbUser);
        // 重定向到原页面
        return "redirect:"+url;
    }

    /**
     * 根据id删除购物车中商品
     * @param id
     * @param user
     * @return
     */
    @RequestMapping("/cart/delete/{id}.action")
    @ResponseBody
    public EgoResult delete(@PathVariable Long id,TbUser user){
        return cartService.deleteById(id, user);
    }

    /**
     * 结算页面
     * @param model
     * @param user
     * @param id 结算的商品id 集合
     * @return
     */
    @RequestMapping("/cart/order-cart.html")
    public String orderCart(Model model, TbUser user,@RequestParam("id") List<Long> id){
        model.addAttribute("cartList", cartService.showOrderCart(user, id));
        return "order-cart";
    }

    /**
     * 修改购物车中商品数量
     * @param id
     * @param num 修改后的商品数量
     * @return
     */
    @PostMapping("/cart/update/num/{id}/{num}.action")
    @ResponseBody
    public EgoResult updateNum(@PathVariable Long id,@PathVariable int num,TbUser user){
        return cartService.updateNum(id, num, user);
    }

    @RequestMapping("/cart/delete/{token}")
    public EgoResult deleteByIds(@RequestBody List<Long> ids,@PathVariable("token") String token){
        return cartService.deleteByIds(ids,token);
    }


}
