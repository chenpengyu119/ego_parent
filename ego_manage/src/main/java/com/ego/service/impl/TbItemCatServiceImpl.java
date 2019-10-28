package com.ego.service.impl;

import com.ego.commons.pojo.EasyUITreeNode;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.pojo.TbItemCat;
import com.ego.service.TbItemCatService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/23 15:33.
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;

    @Override
    public List<EasyUITreeNode> selectById(Long id) {
        List<EasyUITreeNode> nodeList = new ArrayList<>();
        // 查询商品类目
        List<TbItemCat> tbItemCats = tbItemCatDubboService.selectByPid(id);
        for (TbItemCat tbItemCat : tbItemCats){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            //父节点为closed状态
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            nodeList.add(node);
        }
        return nodeList;
    }
}
