package com.ego.search.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemDesc;
import com.ego.search.pojo.SolrEntity;
import com.ego.search.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pengyu
 * @date 2019/9/29 14:53.
 */
@Service
public class SeacheServiceImpl implements SearchService {

    @Reference
    private TbItemDubboService tbItemDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Autowired
    private SolrOperations solrOperations;

    @Value("${custom.solr.num}")
    private int num;

    @Value("${custom.solr.collection}")
    private String  collection1;

    @Override
    public void initData() {

        SolrInputDocument document = null;
        // 查询数据库中数据
        List<TbItem> list = tbItemDubboService.selectList();
        for (TbItem item : list){
            // 商品分类
            TbItemCat cat = tbItemCatDubboService.getById(item.getCid());
            // 商品，描述
            TbItemDesc desc = tbItemDescDubboService.selectById(item.getId());
            document = new SolrInputDocument();
            document.setField("id", item.getId());
            document.setField("item_title", item.getTitle());
            document.setField("item_sell_point", item.getSellPoint());
            document.setField("item_price", item.getPrice());
            document.setField("item_image", item.getImage());
            document.setField("item_category_name", cat.getName());
            if (desc == null){
                desc = new TbItemDesc();
                desc.setItemDesc("");
            }
            document.setField("item_desc", desc.getItemDesc());
            solrOperations.saveDocument(collection1, document);
        }
        // 提交
        solrOperations.commit(collection1);



    }

    @Override
    public Map<String, Object> showData(String q, int page) {

        Map<String,Object> result = new HashMap<>();

        SimpleHighlightQuery query = new SimpleHighlightQuery();
        // 查询条件
        Criteria c = new Criteria("item_keywords");
        // 查询条件的值
        c.is(q);
        query.addCriteria(c);
        // 排序 根据同步时间降序排序
        Sort sort = new Sort(Sort.Direction.DESC,"_version_");
        query.addSort(sort);
        // 分页
        // 从哪行开始
        query.setOffset(num*(page-1)*1L);
        // 每页显示多少行数据
        query.setRows(num);

        // 高亮
        HighlightOptions options = new HighlightOptions();
        // 开标签
        options.setSimplePrefix("<span style='color:red;'>");
        // 闭标签
        options.setSimplePostfix("</span>");
        // 设置高亮列 只设置展示的时候有的列
        options.addField("item_title item_sell_point");
        query.setHighlightOptions(options);
        HighlightPage<SolrEntity> hlp = solrOperations.queryForHighlightPage(collection1, query, SolrEntity.class);

        List<SolrEntity> itemList = new ArrayList<>();

        List<HighlightEntry<SolrEntity>> entryList = hlp.getHighlighted();

        for (HighlightEntry<SolrEntity> hle : entryList){
            // 这是不带高亮的
            SolrEntity entity = hle.getEntity();
            // 带高亮的
            List<HighlightEntry.Highlight> highlights = hle.getHighlights();
            for (HighlightEntry.Highlight hl : highlights){
                if (hl.getField().getName().equals("item_title")){
                    List<String> snipplets = hl.getSnipplets();
                    entity.setTitle(snipplets.get(0));
                }else if (hl.getField().getName().equals("item_sell_point")){
                    List<String> snipplets = hl.getSnipplets();
                    entity.setSellPoint(snipplets.get(0));
                }
            }
            // 设置image[]
            String image = entity.getImage();
            // 因为存储的时候是将url以，分隔
            entity.setImages(image.split(","));
            itemList.add(entity);

        }
        result.put("totalPages", hlp.getTotalPages());
        result.put("page", page);
        result.put("itemList", itemList);
        result.put("query", q);
        return result;
    }

    @Override
    public String syncAdd(Long id) {

        // 查询数据库中的数据
        TbItem item = tbItemDubboService.getById(id);

        // 商品分类
        TbItemCat cat = tbItemCatDubboService.getById(item.getCid());
        // 商品，描述
        TbItemDesc desc = tbItemDescDubboService.selectById(item.getId());
        SolrInputDocument document = new SolrInputDocument();
        document.setField("id", item.getId());
        document.setField("item_title", item.getTitle());
        document.setField("item_sell_point", item.getSellPoint());
        document.setField("item_price", item.getPrice());
        document.setField("item_image", item.getImage());
        document.setField("item_category_name", cat.getName());
        if (desc == null){
            desc = new TbItemDesc();
            desc.setItemDesc("");
        }
        document.setField("item_desc", desc.getItemDesc());
        solrOperations.saveDocument(collection1, document);

        // 提交
        solrOperations.commit(collection1);
        return "success";
    }
}
