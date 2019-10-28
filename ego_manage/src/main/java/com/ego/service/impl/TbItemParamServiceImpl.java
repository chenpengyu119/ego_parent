package com.ego.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.commons.pojo.EgoResult;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.pojo.TbItemParam;
import com.ego.service.TbItemParamService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/24 14:10.
 */
@Service
public class TbItemParamServiceImpl implements TbItemParamService {

    @Reference
    private TbItemParamDubboService tbItemParamDubboService;

    @Override
    public List<TbItemParam> showParam(int page, int rows) {
        return tbItemParamDubboService.selectByPage(page, rows);
    }

    @Override
    public EgoResult showParamByItemCatId(Long itemCatId) {
        TbItemParam tbItemParam = tbItemParamDubboService.selectByItemCatId(itemCatId);
        return EgoResult.ok(tbItemParam);
    }

    @Override
    public EgoResult insert(String paramData,Long itemCatId) {

        TbItemParam tbItemParam = new TbItemParam();
        // 当前日期
        Date now = new Date();
        // 类目id
        tbItemParam.setItemCatId(itemCatId);
        tbItemParam.setCreated(now);
        tbItemParam.setUpdated(now);
        // 参数数据
        tbItemParam.setParamData(paramData);
        int index = 0;
        try {
             index = tbItemParamDubboService.insert(tbItemParam);
        } catch (Exception e) {
            e.printStackTrace();
            return EgoResult.error(e.getMessage());
        }
        if (index==1){
            return EgoResult.ok();
        }
        return EgoResult.error("新增失败");
    }

    @Override
    public EgoResult deleteByIds(String ids) {

        String[] idStrArr = ids.split(",");
        Long[] idArr = new Long[idStrArr.length];
        for (int i = 0; i < idStrArr.length; i++) {
            idArr[i] = Long.parseLong(idStrArr[i]);
        }
        int index = 0;
        try {
            index = tbItemParamDubboService.deleteByIds(idArr);
        } catch (DaoException e) {
            e.printStackTrace();
            return EgoResult.error(e.getMessage());
        }
        if (index>0){
            return EgoResult.ok();
        }
        return EgoResult.error("删除失败");
    }
}
