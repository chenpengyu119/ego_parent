package com.ego.controller;

import com.ego.commons.pojo.EasyUITreeNode;
import com.ego.service.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/23 15:45.
 */
@Controller
public class TbItemCatController {

    @Autowired
    private TbItemCatService tbItemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> showItems(@RequestParam(defaultValue = "0") Long id){
        return tbItemCatService.selectById(id);
    }
}
