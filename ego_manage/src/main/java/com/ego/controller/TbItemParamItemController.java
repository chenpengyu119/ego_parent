package com.ego.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.service.TbItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pengyu
 * @date 2019/9/24 15:34.
 */
@RestController
public class TbItemParamItemController {

    @Autowired
    private TbItemParamItemService tbItemParamItemService;

    @GetMapping("/rest/item/param/item/query/{itemId}")
    public EgoResult showParam(@PathVariable Long itemId){
        return tbItemParamItemService.showItemParamByItemId(itemId);
    }
}
