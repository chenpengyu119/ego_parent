package com.ego.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemDesc;
import com.ego.service.TbItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author pengyu
 * @date 2019/9/23 17:13.
 */
@Controller
public class TbItemDescController {

    @Autowired
    private TbItemDescService tbItemDescService;

    @RequestMapping("/rest/item/query/item/desc/{id}")
    @ResponseBody
    public EgoResult getItemDesc(@PathVariable Long id){
        TbItemDesc itemDesc = tbItemDescService.getById(id);
        if (itemDesc!=null){
            return EgoResult.ok(itemDesc);
        }
        return EgoResult.error("查询商品描述失败");
    }
}
