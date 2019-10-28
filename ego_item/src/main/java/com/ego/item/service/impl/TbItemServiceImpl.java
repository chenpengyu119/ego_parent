package com.ego.item.service.impl;

import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.item.pojo.ItemParam;
import com.ego.item.pojo.TbItemChild;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author pengyu
 * @date 2019/10/8 15:19.
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;
    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${custom.redis.itemDetailKey}")
    private String itemDetailKey;


    Logger logger = LoggerFactory.getLogger(TbItemServiceImpl.class);

    @Override
    public TbItemChild showDetails(Long id) {

        // 处理缓存中商品详情key
        String key  = itemDetailKey+":"+id;

        // 从缓存中取数据
        if (redisTemplate.hasKey(key)) {
            logger.info("商品详情---缓存");
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<TbItemChild>(TbItemChild.class));
            return (TbItemChild) redisTemplate.opsForValue().get(key);
        }

        TbItem item = tbItemDubboService.getById(id);

        TbItemChild child = new TbItemChild();
        BeanUtils.copyProperties(item, child);
        String image = child.getImage();
        child.setImages(image!=null && !"".equals(image)
                ?image.split(",")
                :new String[1]);
        // 将数据放到缓存中
        redisTemplate.opsForValue().set(key, child);
        logger.info("商品详情---mysql");

        return child;
    }

    @Override
    public String showItemDesc(Long id) {
        TbItemDesc desc = tbItemDescDubboService.selectById(id);
        return desc.getItemDesc();
    }

    @Override
    public String showItemParamItem(Long id) {
        TbItemParamItem tbItemParamItem = tbItemParamItemDubboService.selectByItemId(id);

        String paramData = tbItemParamItem.getParamData();
        // 该工具类的返回值就是一个集合，所以json字符串最外层的json数组不需要管，直接定义一个数组内部的实体即可，
        List<ItemParam> itemParams = JsonUtils.jsonToList(paramData, ItemParam.class);
        // 构造html页面
        StringBuilder sb = new StringBuilder();


        for (ItemParam itemParam:itemParams){
            sb.append("<table style='color:gray;'>");
            for (int i = 0; i < itemParam.getParams().size(); i++) {
                sb.append("<tr>");
                if (i==0){
                    sb.append("<td>"+itemParam.getGroup()+"</td>");
                }else {
                    sb.append("<td><td>");
                }
                sb.append("<td>"+itemParam.getParams().get(i).getK()+"<td>");
                sb.append("<td>"+itemParam.getParams().get(i).getV()+"<td>");
                sb.append("</tr>");
            }
            sb.append("</table>");
            sb.append("<hr/>");
        }
        return sb.toString();
    }
}
