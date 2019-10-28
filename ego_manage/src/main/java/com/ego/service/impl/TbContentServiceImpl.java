package com.ego.service.impl;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.rabbitmq.send.Sender;
import com.ego.service.TbContentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/26 19:17.
 */
@Service
public class TbContentServiceImpl implements TbContentService {

    @Reference
    private TbContentDubboService tbContentDubboService;
    /**
     * amqp发送器
     */
    @Autowired
    private Sender sender;

    @Value("${custom.redis.portalAdKey}")
    private String portalAdKey;


    @Override
    public EasyUIDataGrid showContent(Long categoryId, int page, int rows) {

        // 数据
        List<TbContent> list = tbContentDubboService.selectByCategoryIdPage(categoryId, page, rows);
        EasyUIDataGrid dataGrid = new EasyUIDataGrid();
        // 将数据添加到Datagrid
        dataGrid.setRows(list);
        // 设置总记录数
        dataGrid.setTotal(tbContentDubboService.selectCount(categoryId));
        return dataGrid;
    }

    @Override
    public EgoResult addContent(TbContent tbContent) {
        Date now = new Date();
        tbContent.setCreated(now);
        tbContent.setUpdated(now);
        int insert = tbContentDubboService.insert(tbContent);
        if (insert == 1){
            // 发送异步消息清除缓存进行数据同步
            sender.send(portalAdKey);
            return EgoResult.ok();
        }
        return EgoResult.error("新增失败");
    }

    @Override
    public EgoResult deleteContent(String idStr) {

        String[] strs = idStr.split(",");
        Long[] ids = new Long[strs.length];
        int i=0;
        for (String str:strs){
            ids[i++] = Long.parseLong(str);
        }
        int index = 0;
        try {
            index = tbContentDubboService.deleteByIds(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return EgoResult.error("删除失败");
        }
        if (index==1){
            return EgoResult.ok();
        }
        return EgoResult.error("删除失败");
    }
}
