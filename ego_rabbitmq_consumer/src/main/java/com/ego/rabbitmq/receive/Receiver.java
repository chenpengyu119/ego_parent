package com.ego.rabbitmq.receive;

import com.ego.commons.exception.DaoException;
import com.ego.commons.pojo.OrderParam;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengyu
 * @date 2019/9/28 19:18.
 */
@Component
public class Receiver {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Reference
    private TbOrderDubboService tbOrderDubboService;
    @Reference
    private TbItemDubboService tbItemDubboService;

    @Value("${custom.solr.addCon}")
    private String addServer;

    @Value("${custom.redis.itemDetailKey}")
    private String itemDetailKey;

    /**
     * 创建订单后清空购物车接口url
     */
    @Value("${custom.rabbitmq.consumer.deleteCartUrl}")
    private String url;

    @RabbitListener(queues = "redisQueue")
    public void receive(String msg){
        redisTemplate.delete(msg);
    }

    @RabbitListener(queues = "solrQueue")
    public void receiveSolr(Long msg){
        // 调用search的控制器方法进行处理,同步solr数据
        Map<String, String> param = new HashMap<>();
        param.put("id", msg+"");
        String res = HttpClientUtil.doPost(addServer+"addSync", param);
        System.out.println("同步solr数据："+res);
    }

    @RabbitListener(queues = "updateQueue")
    public void receiveUpdate(Long id){
        // 调用search的控制器方法进行处理,同步solr数据
        Map<String, String> param = new HashMap<>();
        param.put("id", id+"");
        String res = HttpClientUtil.doPost(addServer+"addSync", param);
        System.out.println("同步solr数据："+res);

        // 同步redis数据 这里如果是修改的话数据查询太麻烦，所以还是直接删除吧
        String key = itemDetailKey+":"+id;
        redisTemplate.delete(key);
    }

    @RabbitListener(queues = "createOrderQueue")
    public void receiveOrderCreate(OrderParam orderParam){
        // 订单实体
        TbOrder tbOrder = orderParam.getTbOrder();
        // 订单项集合
        List<TbOrderItem> tbOrderItems = orderParam.getOrderItems();
        // 快递信息对象
        TbOrderShipping tbOrderShipping = orderParam.getOrderShipping();
        //创建订单并修改商品库存
        try {
            int indexInsert = tbOrderDubboService.insertOrder(tbOrder, tbOrderItems, tbOrderShipping);
            if (indexInsert==1){
                // 删除对应购物车数据
                List<Long> idList = new ArrayList<>();
                for (TbOrderItem item : tbOrderItems){
                    idList.add(Long.parseLong(item.getItemId()));
                }
                // 登录用户信息token
                String token = orderParam.getToken();

                // 调用Cart模块api删除购物车中已结算商品，根据微服务拆分原则，该操作需要放到cart项目中。
                String jsonParam = JsonUtils.objectToJson(idList);
                // 该方法会将参数放到请求体中，对应的控制器中应该在接收参数时使用@RequestBody注解将请求体中数据转换为实体。
                // token使用rest风格传递
                HttpClientUtil.doPostJson(url+token,jsonParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
