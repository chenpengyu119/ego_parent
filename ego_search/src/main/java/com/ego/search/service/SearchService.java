package com.ego.search.service;

import java.util.Map;

/**
 * @author pengyu
 * @date 2019/9/29 14:52.
 */
public interface SearchService {

    /**
     * 初始化solr数据
     */
    void initData();

    /**
     * 查询数据
     * @param q
     * @param page
     * @return
     */
    Map<String,Object> showData(String q,int page);

    /**
     * 后台新增数据时进行数据同步
     * @param id
     * @return
     */
    String syncAdd(Long id);
}
