package com.ego.portal.service.impl;

import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.pojo.PortalAd;
import com.ego.portal.service.PortalService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/28 16:15.
 */
@Service
public class PortalServiceImpl implements PortalService {

    @Reference
    private TbContentDubboService tbContentDubboService;
    @Value("${custom.portal.bigAdNum}")
    private int bigAdNum;
    /**
     * 广告类型id
     */
    @Value("${custom.bigAd.id}")
    private Long categoryId;

    /**
     * 缓存中key
     */
    @Value("${custom.redis.portalAdKey}")
    private String portalAdKey;

    private Logger logger = LoggerFactory.getLogger(PortalService.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;



    @Override
    public String showPortalAd() {

        // 判断缓存中是否存在
        if (redisTemplate.hasKey(portalAdKey)){
            logger.info("从缓存取大广告");
            // 设置值类型为String
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            return (String) redisTemplate.opsForValue().get(portalAdKey);
        }

        List<TbContent> list = tbContentDubboService.selectTopNByCategoryId(categoryId, 1, bigAdNum);
        List<PortalAd> adList = new ArrayList<>();
        for (TbContent content:list){
            PortalAd ad = new PortalAd();
            ad.setSrcB(content.getPic2());
            ad.setHeight(240);
            ad.setWidth(670);
            ad.setSrc(content.getPic());
            ad.setWidthB(550);
            ad.setHref(content.getUrl());
            ad.setHeightB(240);
            ad.setAlt("");
            // 添加到新的集合中
            adList.add(ad);
        }
        // 加到缓存中
        // 因为序列化器有记忆功能，所以每次都添加需要设置
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.opsForValue().set(portalAdKey, adList);
        logger.info("从数据库中取大广告");
        return JsonUtils.objectToJson(adList);
    }
}
