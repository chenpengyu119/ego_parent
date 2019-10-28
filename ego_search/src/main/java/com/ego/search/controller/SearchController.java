package com.ego.search.controller;

import com.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author pengyu
 * @date 2019/9/29 14:39.
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search.html")
    public String showSearch(String q, Model model,@RequestParam(defaultValue = "1") int page){
        Map<String, Object> map = searchService.showData(q, page);
        model.addAllAttributes(map);
        return "search";
    }

    /**
     * 初始化solr数据
     * @return
     */
    @RequestMapping("/solr/init")
    @ResponseBody
    public String initData(){
        long startAt = System.currentTimeMillis();
        searchService.initData();
        long endAt = System.currentTimeMillis();
        return "初始化完成,耗时："+(endAt-startAt)/1000+"秒";
    }

    /**
     * 新增后同步数据
     * @param id
     * @return
     */
    @RequestMapping("/addSync")
    @ResponseBody
    public String syncAddData(Long id){
        return searchService.syncAdd(id);
    }
}
