package com.ego.service.impl;

        import com.ego.commons.exception.DaoException;
        import com.ego.commons.pojo.EasyUIDataGrid;
        import com.ego.commons.pojo.EgoResult;
        import com.ego.commons.utils.IDUtils;
        import com.ego.dubbo.service.TbItemDubboService;
        import com.ego.pojo.TbItem;
        import com.ego.pojo.TbItemDesc;
        import com.ego.pojo.TbItemParamItem;
        import com.ego.rabbitmq.send.Sender;
        import com.ego.service.TbItemService;
        import org.apache.dubbo.config.annotation.Reference;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.Date;
        import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/20 16:59.
 */
@Service
public class TbItemServiceImpl implements TbItemService {

    @Reference
    private TbItemDubboService tbItemDubboService;

    @Autowired
    private Sender sender;

    @Override
    public EasyUIDataGrid showItem(int page, int rows) {

        List<TbItem> tbItems = tbItemDubboService.selectByPage(page, rows);
        long count = tbItemDubboService.selectCount();
        EasyUIDataGrid dataGrid = new EasyUIDataGrid(tbItems, count);
        return dataGrid;
    }

    @Override
    public EgoResult updateStatusByIds(String statusStr, String idsStr) {

        byte status = "delete".equals(statusStr) ? (byte) 3
                : ("instock".equals(statusStr)
                ? (byte) 2 : (byte) 1);

        String[] ids = idsStr.split(",");

        // String转long
        long[] idArr = new long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            idArr[i] = Long.parseLong(ids[i]);
        }
        int updateIndex = 0;
        try {
            updateIndex = tbItemDubboService.updateStatusByIds(status, idArr);
        } catch (DaoException e) {
            e.printStackTrace();
            return EgoResult.error(e.getMessage());
        }
        if (updateIndex == 1) {
            return EgoResult.ok();
        }
        return EgoResult.error("更新商品状态失败");
    }

    @Override
    public int insertItem(TbItem tbItem, String desc,String itemParams) throws Exception{

        Date now = new Date();
        // 商品id
        Long id = IDUtils.genItemId();
        tbItem.setId(id);
        // 更新时间
        tbItem.setUpdated(now);
        // 创建时间
        tbItem.setCreated(now);
        // 状态
        tbItem.setStatus((byte)1);

        // 商品描述
        TbItemDesc tbItemDesc = new TbItemDesc();
        // 商品id
        tbItemDesc.setItemId(id);
        // 描述
        tbItemDesc.setItemDesc(desc);
        // 创建时间
        tbItemDesc.setCreated(now);
        // 更新时间
        tbItemDesc.setUpdated(now);

        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        // 商品id
        tbItemParamItem.setItemId(id);
        // 创建时间
        tbItemParamItem.setCreated(now);
        // 参数
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setUpdated(now);

        int index = tbItemDubboService.insertItem(tbItem, tbItemDesc,tbItemParamItem);
        if (index == 1) {

            // 发送数据同步的异步消息
            sender.sendSolr(id);
            return 1;
        }

        return 0;
    }

    @Override
    public int updateItem(TbItem tbItem, String desc,String itemParams,Long itemParamId) throws Exception {

        Date now = new Date();
        // 更新时间
        tbItem.setUpdated(now);
        // 商品描述
        TbItemDesc tbItemDesc = new TbItemDesc();
        // 商品id
        tbItemDesc.setItemId(tbItem.getId());
        // 描述
        tbItemDesc.setItemDesc(desc);
        // 更新时间
        tbItemDesc.setUpdated(now);

        // 商品规格参数
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        // 规格参数id
        tbItemParamItem.setId(itemParamId);
        // 参数信息
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setUpdated(now);
        int index = tbItemDubboService.updateItemByItem(tbItem, tbItemDesc,tbItemParamItem);
        if (index==1){

            // 修改后同步solr数据
            // 同步redis-商品详情数据
            // 只发一条消息给Consumer
            sender.sendUpdate(tbItem.getId());
            return 1;
        }
        return 0;
    }
}
