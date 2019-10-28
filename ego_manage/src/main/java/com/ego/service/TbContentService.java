package com.ego.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

/**
 * @author pengyu
 * @date 2019/9/26 19:15.
 */
public interface TbContentService {

    /**
     * 根据categoryId分页查询内容
     * @param categoryId 分类id
     * @param page 当前页码
     * @param rows 最大显示行数
     * @return
     */
    EasyUIDataGrid showContent(Long categoryId,int page,int rows);

    /**
     * 新增
     * @param tbContent
     * @return
     */
    EgoResult addContent(TbContent tbContent);

    /**
     * 删除多条内容
     * @param idStr
     * @return
     */
    EgoResult deleteContent(String idStr);
}
