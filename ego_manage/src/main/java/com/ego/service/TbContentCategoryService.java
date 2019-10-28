package com.ego.service;

import com.ego.commons.pojo.EasyUITreeNode;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/26 10:02.
 */
public interface TbContentCategoryService {
    /**
     * 显示类目
     * @param pid
     * @return
     */
    List<EasyUITreeNode> showCategory(Long pid);

    /**
     * 添加分类节点
     * @param tbContentCategory
     * @return
     */
    EgoResult addNode(TbContentCategory tbContentCategory);

    /**
     * 根据主键更新分类名称
     * @param tbContentCategory
     * @return
     */
    EgoResult updateNameById(TbContentCategory tbContentCategory);

    /**
     * 根据id删除分类节点
     * @param id
     * @return
     */
    EgoResult deleteByPk(Long id);
}
