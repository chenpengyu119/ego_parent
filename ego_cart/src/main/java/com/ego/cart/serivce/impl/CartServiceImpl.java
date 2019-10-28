package com.ego.cart.serivce.impl;

import com.ego.cart.pojo.CartPojo;
import com.ego.cart.serivce.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CartConst;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.commons.utils.ServletUtil;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbUser;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author pengyu
 * @date 2019/10/10 16:14.
 */
@Service
public class CartServiceImpl implements CartService {

    @Reference
    private TbItemDubboService tbItemDubboService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public void addCart(Long id, int num, TbUser user) {

        // 查询商品信息
        TbItem tbItem = tbItemDubboService.getById(id);
        // 创建购物车中商品实体
        CartPojo cartPojo = new CartPojo();
        // 给购物车中商品实体赋值
        BeanUtils.copyProperties(tbItem, cartPojo);
        // 设置images
        String image = tbItem.getImage();
        if (image!=null&&!image.equals("")){
            cartPojo.setImages(image.split(","));
        }else {
            cartPojo.setImages(new String[1]);
        }

        // 用户未登录，将购物车数据放到cookies中
        if (user==null){
            // 先判断cookie中是否已存在该种商品的购物车
            String cookieValue = CookieUtils.getCookieValue(ServletUtil.getRequest(), CartConst.TMP_CART_COOKIES_KEY, true);
            if (Strings.isNotBlank(cookieValue)){
                // 购物车不为空

                // 取出临时购物车数据
                List<CartPojo> cookieList = JsonUtils.jsonToList(cookieValue, CartPojo.class);
                CartPojo changeNumTmpCartPojo = null;
                // 判断当前商品是否在购物车已存在
                for (CartPojo cp : cookieList){
                    if (cp.getId().equals(id)){
                        // 已经存在，获取该种商品
                        changeNumTmpCartPojo = cp;
                        break;
                    }
                }
                if (changeNumTmpCartPojo!=null){
                    // 已经存在该商品，直接修改数量
                    changeNumTmpCartPojo.setNum(changeNumTmpCartPojo.getNum()+num);
                }else {
                    // 之前不存在该商品，需要新增
                    // 设置商品数量
                    cartPojo.setNum(num);
                    // 加到集合中
                    cookieList.add(cartPojo);
                }
                // 将集合存储到cookies
                saveToCookie(cookieList);

            }else {
                // 购物车为空
                List<CartPojo> cartPojoList = new ArrayList<>();
                // 设置商品数量
                cartPojo.setNum(num);
                cartPojoList.add(cartPojo);
                saveToCookie(cartPojoList);
            }
        }else {
            // 用户已登录，将购物车数据放到redis中
            // 生成商品信息的键名   cart:用户id
            String redisKey = CartConst.CART_REDIS_KEY_PREFIX+user.getId();

            // Cookies和redis都是先放到list，再放进去，不同是cookies因为没有用户之分，所以使用的是固定key，redis通过key区分用户
            // 判断购物车是否为空
            if (redisTemplate.hasKey(redisKey)){
               // 该用户购物车不为空
                // 取出该用户购物车数据
                List<CartPojo> cartPojoList = getFromRedis(user);
                // 判断购物车中是否存在当前商品
                // 记录当前商品的信息
                CartPojo tmpCart = null;
                for (CartPojo cp:cartPojoList){
                    if (cp.getId().equals(cartPojo.getId())){
                        // 已存在，获取到该商品信息
                        tmpCart = cp;
                        break;
                    }
                }
                if (tmpCart==null){
                    // 不存在，需新增
                    // 设置商品数量
                    cartPojo.setNum(num);
                    cartPojoList.add(cartPojo);
                    // 存储到redis
                    saveToRedis(user, cartPojoList);
                }else {
                    // 已经存在该商品信息，直接修改数量
                    tmpCart.setNum(tmpCart.getNum()+num);
                    // 将数据放回redis
                    saveToRedis(user, cartPojoList);
                }
            }else {
              // 该用户购物车为空
                List<CartPojo> newCartPojoList = new ArrayList<>();
                // 设置商品数量
                cartPojo.setNum(num);
                newCartPojoList.add(cartPojo);
                // 存储到redis
                saveToRedis(user, newCartPojoList);
            }
        }
    }

    @Override
    public List<CartPojo> showCart(TbUser user) {

        // 判断是否登录
        if (user==null){
          return getFromCookie();
        }else {
            return getFromRedis(user);
        }
    }

    @Override
    public void merge(TbUser user) {

        // 先取出cookies中的
        List<CartPojo> cookieFrom  = getFromCookie();

        // 再取出redis中的
        List<CartPojo> redisFrom = getFromRedis(user);

        // 合并
        // 创建新集合
        List<CartPojo> mergeCartList = new ArrayList<>();
        // 遍历 先添加的在上面显示，先添加cookie的
        for (CartPojo cpCookie : cookieFrom){
            for (CartPojo cpRedis : redisFrom){
                if (cpCookie.getId().equals(cpRedis.getId())){
                    // 临时购物车和用户购物车有相同商品
                    cpCookie.setNum(cpCookie.getNum()+cpRedis.getNum());
                    // 添加到合成集合中
                    mergeCartList.add(cpCookie);
                    // 从redis中移除该相同商品
                    redisFrom.remove(cpRedis);
                    break;
                }
            }
            // 临时和用户购物车不存在相同的该商品
            mergeCartList.add(cpCookie);
        }

        for (CartPojo cp : redisFrom){
            mergeCartList.add(cp);
        }
        // 放回到redis中
       saveToRedis(user, mergeCartList);
        // 清除cookies中的购物车数据
        CookieUtils.deleteCookie(ServletUtil.getRequest(), ServletUtil.getResponse(), CartConst.TMP_CART_COOKIES_KEY);
    }

    @Override
    public EgoResult deleteById(Long id,TbUser user) {

        // 判断是否登录
        if (user==null){
            // 从cookie中删除
            List<CartPojo> fromCookie = getFromCookie();
            Iterator<CartPojo> iterator = fromCookie.iterator();
            while (iterator.hasNext()){
                CartPojo pojo = iterator.next();
                if (pojo.getId().equals(id)){
                    fromCookie.remove(pojo);
                    break;
                }
            }
            saveToCookie(fromCookie);

        }else {
            // 从redis中删除
            List<CartPojo> fromRedis = getFromRedis(user);
            Iterator<CartPojo> iterator = fromRedis.iterator();
            while (iterator.hasNext()){
                CartPojo pojo = iterator.next();
                if (pojo.getId().equals(id)){
                    iterator.remove();
                    break;
                }
            }
            saveToRedis(user, fromRedis);
        }

        return EgoResult.ok();
    }

    @Override
    public List<CartPojo> showOrderCart(TbUser user, List<Long> id) {

        // 取出所有用户购物车数据
        List<CartPojo> fromRedis = getFromRedis(user);
        // 返回值类型
        List<CartPojo> orderPojoList = new ArrayList<>();
        // 遍历查找结算商品
        for (CartPojo pojo : fromRedis){
            if (id.contains(pojo.getId())){
                TbItem item = tbItemDubboService.getById(pojo.getId());
                // 设置商品库存是否足够
                pojo.setEnough(item.getNum()>=pojo.getNum());
                orderPojoList.add(pojo);
            }
        }
        return orderPojoList;
    }

    @Override
    public EgoResult updateNum(Long id, int num,TbUser user) {

        // 判断是否登录
        if (user==null){
            // 未登录，修改cookie
            List<CartPojo> fromCookie = getFromCookie();
            for (CartPojo cp : fromCookie){
                if (cp.getId().equals(id)){
                    cp.setNum(num);
                    saveToCookie(fromCookie);
                    return EgoResult.ok();
                }
            }
        }else {
            // 修改redis
            List<CartPojo> fromRedis = getFromRedis(user);
            for (CartPojo cp : fromRedis){
                if (cp.getId().equals(id)){
                    cp.setNum(num);
                    saveToRedis(user, fromRedis);
                    return EgoResult.ok();
                }
            }
        }
        return EgoResult.error("修改商品数量失败");
    }

    /**
     * 因为跨项目且使用的是HttpClient，无法获取用户信息，所以无法使用封装的操作cookie和redis的方法
     * @param ids
     * @param token 登录用户token
     * @return
     */
    @Override
    public EgoResult deleteByIds(List<Long> ids,String token) {

        // 取出该用户购物车数据
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        String redisCartJson = (String) redisTemplate.opsForValue().get(token);
        // json转集合
        List<CartPojo> fromRedis = JsonUtils.jsonToList(redisCartJson, CartPojo.class);
        // 创建迭代器
        Iterator iterator = fromRedis.iterator();
        while (iterator.hasNext()){
            // 控制循环结束
            if (ids.size()==0){
                break;
            }
            // 接收商品信息
            CartPojo cp = (CartPojo) iterator.next();
            // 如果存在于ids中
            if (ids.contains(cp.getId())){
                // 移除商品
                iterator.remove();
                int index = ids.indexOf(cp.getId());
                ids.remove(index);
            }
        }
        // 存储到redis
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.opsForValue().set(token, fromRedis);
        return EgoResult.ok();
    }

    /**
     * 从cookie中取购物车数据
     * @return
     */
    private List<CartPojo> getFromCookie(){
        // 未登录，去cookie中取商品信息
        String cookieValue = CookieUtils.getCookieValue(ServletUtil.getRequest(), CartConst.TMP_CART_COOKIES_KEY, true);
        if (Strings.isNotBlank(cookieValue)) {
            // 购物车不为空
            // 取出临时购物车数据
            List<CartPojo> cookieList = JsonUtils.jsonToList(cookieValue, CartPojo.class);
            return cookieList;
        }else {
            return new ArrayList<>();
        }
    }

    /**
     * 从redis中取购物车数据
     * @return
     */
    private List<CartPojo> getFromRedis(TbUser user){
        // 已登录，去redis中获取商品信息
        // 取出该用户购物车数据
        // 生成商品信息的键名   cart:用户id
        String redisKey = CartConst.CART_REDIS_KEY_PREFIX+user.getId();
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        String redisCartJson = (String) redisTemplate.opsForValue().get(redisKey);
        // json转集合
        List<CartPojo> cartPojoList = JsonUtils.jsonToList(redisCartJson, CartPojo.class);
        return cartPojoList;
    }

    /**
     * 存储到cookie中
     * @param cartPojoList
     */
    private void saveToCookie(List<CartPojo> cartPojoList){
        String cookieJson = JsonUtils.objectToJson(cartPojoList);
        CookieUtils.setCookie(ServletUtil.getRequest(), ServletUtil.getResponse()
                , CartConst.TMP_CART_COOKIES_KEY, cookieJson, CartConst.COOKIES_TIMEOUT_SECOND, true);
    }

    /**
     * 存储到redis中
     * @param user
     * @param cartPojoList
     */
    private void saveToRedis(TbUser user,List<CartPojo> cartPojoList){
        String redisKey = CartConst.CART_REDIS_KEY_PREFIX+user.getId();
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.opsForValue().set(redisKey, cartPojoList);
    }



}
