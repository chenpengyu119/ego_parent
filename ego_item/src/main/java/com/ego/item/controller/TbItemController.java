package com.ego.item.controller;

import com.ego.item.pojo.TbItemChild;
import com.ego.item.service.TbItemService;
import com.ego.pojo.TbItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author pengyu
 * @date 2019/10/8 14:45.
 */
@Controller
public class TbItemController {

    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/item/{id}.html")
    public String showDetails(@PathVariable Long id, Model model){
        TbItemChild child = tbItemService.showDetails(id);
        model.addAttribute("item", child);
        return "item";
    }

    @RequestMapping("/item/desc/{id}.html")
    @ResponseBody
    public String showItemDesc(@PathVariable Long id){
        return tbItemService.showItemDesc(id);
    }


    @RequestMapping("/item/param/{itemid}.html")
    @ResponseBody
    public String showItemParamItem(@PathVariable Long itemid){
        return tbItemService.showItemParamItem(itemid);
    }




}
