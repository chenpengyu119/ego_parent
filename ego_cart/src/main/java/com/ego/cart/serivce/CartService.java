package com.ego.cart.serivce;

import com.ego.cart.pojo.CartPojo;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/10/10 16:14.
 */
public interface CartService {

    /**
     * 加入购物车
     * @param id 商品编号
     * @param num 加购商品数量
     * @param user 登录用户信息
     * @return
     */
    void addCart(Long id, int num, TbUser user);

    /**
     * 准备购物车页面数据
     * @return
     */
    List<CartPojo> showCart(TbUser user);

    /**
     * 合并购物车
     * @param user
     */
    void merge(TbUser user);

    /**
     * 从购物车中删除商品
     * @param id
     * @param user 登录用户
     * @return
     */
    EgoResult deleteById(Long id,TbUser user);

    /**
     * 根据id从用户购物车中查出所有要结算商品的信息
     * @param user
     * @param id
     * @return
     */
    List<CartPojo> showOrderCart(TbUser user,List<Long> id);

    /**
     * 修改购物车中商品数量
     * @param id 商品编号
     * @param num 修改后的商品数量
     * @return
     */
    EgoResult updateNum(Long id, int num,TbUser user);

    /**
     * 根据ids删除购物车中商品
     * @param ids
     * @param token 登录用户token
     * @return
     */
    EgoResult deleteByIds(List<Long> ids,String token);
}
