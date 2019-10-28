package com.ego.dubbo.service;

import com.ego.pojo.TbContent;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/26 17:25.
 */
public interface TbContentDubboService {

    /**
     * 根据categoryId分页查询网站内容
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<TbContent> selectByCategoryIdPage(Long categoryId,int pageNum,int pageSize);

    /**
     * 条件查询总数量
     * @param categoryId
     * @return
     */
    Long selectCount(Long categoryId);

    /**
     * 新增
     * @param tbContent
     * @return
     */
    int insert(TbContent tbContent);

    /**
     * 根据categoryId分页查询内容并降序排序
     * @param categoryId 分类id
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<TbContent> selectTopNByCategoryId(Long categoryId,int pageNum,int pageSize);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteByIds(Long[] ids);
}
