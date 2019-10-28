package com.ego.item.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.item.pojo.Category;
import com.ego.item.pojo.CategoryResult;
import com.ego.item.pojo.TbItemChild;
import com.ego.item.service.TbItemCatService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/27 15:43.
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {

    private Logger log = LogManager.getLogger(TbItemCatServiceImpl.class);

    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Autowired
    private RedisTemplate<String,Object> getTemplate;
    @Value("${custom.redis.portalNavKey}")
    private String portalNavKey;

    /**
     * 构造导航菜单数据
     * @return
     */
    @Override
    public CategoryResult showCategory() {

        // 从缓存中获取数据
        if (getTemplate.hasKey(portalNavKey)){
            // 设置值序列化器的类型
            getTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<CategoryResult>(CategoryResult.class));
            log.info("从缓存中获取数据");
            // 获取值
            return (CategoryResult) getTemplate.opsForValue().get(portalNavKey);
        }


        CategoryResult cr = new CategoryResult();

        List<Category> data = new ArrayList<>();

        // 先查询第一层，因本项目导航菜单只有三层，所以不使用递归
        List<TbItemCat> firstCat = tbItemCatDubboService.selectByPid(0L);
        // 构造数据
        for (TbItemCat cat:firstCat){
            Category category = new Category();
            category.setU("/products/"+cat.getId()+".html");
            category.setN("<a href='/products/"+cat.getId()+".html'>"+cat.getName()+"</a>");
            // 查询第二级菜单,父id为当前菜单id
            List<TbItemCat> secondList = tbItemCatDubboService.selectByPid(cat.getId());
            // 构造二级菜单数据
            List<Category> second = new ArrayList<>();
            for (TbItemCat cat2:secondList){
                Category category2 = new Category();
                category2.setU("/products/"+cat2.getId()+".html");
                category2.setN("<a href='/products/"+cat2.getId()+".html'>"+cat2.getName()+"</a>");
                // 查第三层
                List<TbItemCat> thirdList = tbItemCatDubboService.selectByPid(cat2.getId());
                List<String> third = new ArrayList<>();
                for (TbItemCat cat3:thirdList){
                   third.add("/products/"+cat3.getId()+".html|"+cat3.getName());
                }
                category2.setI(third);
                second.add(category2);
            }
            category.setI(second);
            data.add(category);
        }
        cr.setData(data);
        // 将值放到redis缓存中
        getTemplate.opsForValue().set(portalNavKey, cr);
        log.info("从MySQL中获取数据");
        return cr;
    }


}
