package com.ego.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItem;
import com.ego.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author pengyu
 * @date 2019/9/20 17:03.
 */
@Controller
public class TbItemController {

    @Autowired
    private TbItemService tbItemService;

    /**
     * 展示商品详情
     * @param page 当前页面
     * @param rows  最大显示行数
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGrid showItem(int page,int rows){
        return tbItemService.showItem(page, rows);
    }

    @RequestMapping("/rest/item/{statusStr}")
    @ResponseBody
    public EgoResult updateStatus(@PathVariable String statusStr,String ids){
        return tbItemService.updateStatusByIds(statusStr, ids);
    }

    @RequestMapping("item/save")
    @ResponseBody
    public EgoResult insertItem(TbItem tbItem,String desc,String itemParams){

        try {
            int index = tbItemService.insertItem(tbItem, desc,itemParams);
            if (index==1){
                return EgoResult.ok();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return EgoResult.error(e.getMessage());
        }
        return EgoResult.error("新增商品失败");
    }

    @RequestMapping("/rest/item/update")
    @ResponseBody
    public EgoResult updateItem(TbItem tbItem,String desc,String itemParams,Long itemParamId){
        int index = 0;
        try {
            index = tbItemService.updateItem(tbItem, desc,itemParams,itemParamId);
        } catch (Exception e) {
            e.printStackTrace();
            return EgoResult.error(e.getMessage());
        }
        if (index==1){
            return EgoResult.ok();
        }
        return EgoResult.error("更新商品信息失败");
    }
}
