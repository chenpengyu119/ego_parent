package com.ego.service.impl;

import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.pojo.TbItemParamItem;
import com.ego.service.TbItemParamItemService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pengyu
 * @date 2019/9/24 15:32.
 */
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {

    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;

    @Override
    public EgoResult showItemParamByItemId(Long itemIde) {
        TbItemParamItem tbItemParamItem = tbItemParamItemDubboService.selectByItemId(itemIde);
        return EgoResult.ok(tbItemParamItem);
    }
}
