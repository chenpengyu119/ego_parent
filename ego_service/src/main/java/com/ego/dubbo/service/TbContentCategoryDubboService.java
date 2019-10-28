package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/26 9:59.
 */
public interface TbContentCategoryDubboService {

    /**
     * 查询类目
     * @param pid
     * @return
     */
    List<TbContentCategory> selectByPid(Long pid);

    /**
     * 新增分类
     * @param tbContentCategory
     * @return
     */
    TbContentCategory insert(TbContentCategory tbContentCategory) throws DaoException;

    /**
     * 根据主键更新
     * @param tbContentCategory
     * @return
     */
    int updateByPk(TbContentCategory tbContentCategory);

    /**
     * 主键查询
     * @param id
     * @return
     */
    TbContentCategory selectByPk(Long id);


    /**
     * 根据主键更新，如果父节点只有删除节点一个子节点，则设置父节点isParent为false
     * @param tbContentCategory
     * @return
     */
    int updateAndParent(TbContentCategory tbContentCategory) throws DaoException;

}
