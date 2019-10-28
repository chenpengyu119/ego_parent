package com.ego.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.commons.pojo.EasyUITreeNode;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.pojo.TbContentCategory;
import com.ego.service.TbContentCategoryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/26 10:03.
 */
@Service
public class TbContentCategoryServiceImopl implements TbContentCategoryService {

    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboService;
    @Override
    public List<EasyUITreeNode> showCategory(Long pid) {
        List<TbContentCategory> list = tbContentCategoryDubboService.selectByPid(pid);
        List<EasyUITreeNode> treeList = new ArrayList<>();
        for (TbContentCategory category : list){
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent()?"closed":"open");
            treeList.add(node);
        }
        return treeList;
    }

    @Override
    public EgoResult addNode(TbContentCategory tbContentCategory) {

        // 查询父节点所有子节点
        List<TbContentCategory> children = tbContentCategoryDubboService.selectByPid(tbContentCategory.getParentId());
        String name = tbContentCategory.getName();
        for (TbContentCategory category : children){
            if (!StringUtils.isEmpty(category.getName()) && name!=null && name.equals(category.getName()) ){
                return EgoResult.error("名称重复");
            }
        }

        Date now = new Date();
        // 设置为叶子节点
        tbContentCategory.setIsParent(false);
        // 创建时间
        tbContentCategory.setCreated(now);
        // 排序id
        tbContentCategory.setSortOrder(1);
        // 状态，默认为1:新建
        tbContentCategory.setStatus(1);
        tbContentCategory.setUpdated(now);
        TbContentCategory result = null;
        try {
            result = tbContentCategoryDubboService.insert(tbContentCategory);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        if (result != null){
            return EgoResult.ok(result);
        }
        return EgoResult.error("新增失败");
    }

    @Override
    public EgoResult updateNameById(TbContentCategory tbContentCategory) {

        // 查询当前节点
        TbContentCategory current = tbContentCategoryDubboService.selectByPk(tbContentCategory.getId());
        // 父节点id
        Long parentId = current.getParentId();
        // 查询父节点所有子节点
        List<TbContentCategory> children = tbContentCategoryDubboService.selectByPid(parentId);
        // 判断是否有重名分类
        String name = tbContentCategory.getName();
        for (TbContentCategory child : children){
            if (name!=null && name.equals(child.getName())){
                return EgoResult.error("该名称已存在");
            }
        }
        // 设置更新时间
        int updateName = tbContentCategoryDubboService.updateByPk(tbContentCategory);
        if (updateName==1){
            return EgoResult.ok();
        }
        return EgoResult.error("更新失败");
    }

    @Override
    public EgoResult deleteByPk(Long id) {

        TbContentCategory category = new TbContentCategory();
        category.setId(id);
        category.setUpdated(new Date());
        category.setStatus(2);

        int index = 0;
        try {
            index = tbContentCategoryDubboService.updateAndParent(category);
        } catch (DaoException e) {
            e.printStackTrace();
            return EgoResult.error(e.getMessage());
        }
        if (index == 1){
            return EgoResult.ok();
        }
        return EgoResult.error("删除失败");
    }


}
