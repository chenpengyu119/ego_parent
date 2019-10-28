package com.ego.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;
import com.ego.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengyu
 * @date 2019/9/26 19:23.
 */
@RestController
public class TbContentController {

    @Autowired
    private TbContentService tbContentService;

    @GetMapping("/content/query/list")
    public EasyUIDataGrid showContent(Long categoryId,int page,int rows){
        return tbContentService.showContent(categoryId, page,rows);
    }

    @PostMapping("/content/save")
    public EgoResult addContent(TbContent tbContent){
        return tbContentService.addContent(tbContent);
    }

    @PostMapping("/content/delete")
    public EgoResult delete(String ids){
        return tbContentService.deleteContent(ids);
    }

}
