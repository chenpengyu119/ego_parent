package com.ego.controller;

import com.ego.commons.pojo.EasyUITreeNode;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;
import com.ego.service.TbContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/26 15:23.
 */
@RestController
public class TbContentCategoryController {

    @Autowired
    private TbContentCategoryService tbContentCategoryService;

    @GetMapping("/content/category/list")
    public List<EasyUITreeNode> showCategory(@RequestParam(defaultValue = "0") Long id){
        return tbContentCategoryService.showCategory(id);
    }

    @PostMapping("/content/category/create")
    public EgoResult addNode(TbContentCategory tbContentCategory) {
        return tbContentCategoryService.addNode(tbContentCategory);
    }

    @PostMapping("/content/category/update")
    public EgoResult updateName(TbContentCategory tbContentCategory){
        return tbContentCategoryService.updateNameById(tbContentCategory);
    }

    @PostMapping("/content/category/delete/")
    public EgoResult deleteById(Long id){
        return tbContentCategoryService.deleteByPk(id);
    }
}
