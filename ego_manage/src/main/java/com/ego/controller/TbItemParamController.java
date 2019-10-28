package com.ego.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;
import com.ego.service.TbItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/24 14:11.
 */
@RestController
public class TbItemParamController {

    @Autowired
    private TbItemParamService tbItemParamService;

    @GetMapping("/item/param/list")
    public List<TbItemParam> showParam(int page,int rows){
        return tbItemParamService.showParam(page, rows);
    }

    @GetMapping("/item/param/query/itemcatid/{itemCatId}")
    public EgoResult showParamByItemCatId(@PathVariable Long itemCatId){
        return tbItemParamService.showParamByItemCatId(itemCatId);
    }

    @PostMapping("/item/param/save/{itemCatId}")
    public EgoResult addParam(String paramData,@PathVariable Long itemCatId){
        return tbItemParamService.insert(paramData, itemCatId);
    }

    @PostMapping("/item/param/delete")
    public EgoResult deleteParam(String ids){
        return tbItemParamService.deleteByIds(ids);
    }

}
