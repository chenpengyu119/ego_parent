package com.ego.item.controller;

import com.ego.item.pojo.CategoryResult;
import com.ego.item.service.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/27 15:37.
 */
@Controller
public class TbItemCatController {

    @Autowired
    private TbItemCatService tbItemCatService;

    @RequestMapping("/rest/itemcat/all")
    @ResponseBody
    @CrossOrigin
    public CategoryResult showCategory(){
        return tbItemCatService.showCategory();
    }


}
