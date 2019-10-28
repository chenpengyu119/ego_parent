package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemExample;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/20 16:39.
 */
@Service
public class TbItemDubboServiceImpl implements TbItemDubboService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    /**
     * @param page 当前页码
     * @param rows 页面最大显示行数
     * @return
     */
    @Override
    public List<TbItem> selectByPage(int page, int rows) {
        // 这里不需要计算从第几行开始，直接传入起始行即可
        PageHelper.startPage(page, rows);
        // 使用where从句时必须使用包含Example的方法,此处不需要where条件所以传值null
        List<TbItem> itemList = tbItemMapper.selectByExample(null);
        PageInfo<TbItem> pageInfo = new PageInfo<>(itemList);
       // return pageInfo.getList();
        return itemList;
    }

    @Override
    public long selectCount() {
        return tbItemMapper.countByExample(null);
    }

    @Transactional(rollbackFor = DaoException.class)
    @Override
    public int updateStatusByIds(byte status, long[] ids) {

        int count = 0;
        // 当前日期
        Date now = new Date();
        for (int i = 0; i < ids.length; i++) {
            TbItem item = new TbItem();
            item.setId(ids[i]);
            // 状态
            item.setStatus(status);
            // 更新日期
            item.setUpdated(now);
            count = count + tbItemMapper.updateByPrimaryKeySelective(item);
        }
        if (count == ids.length) {
            return 1;
        }
        throw new DaoException("更新商品状态失败");
    }

    /**
     * 新增商品信息和描述
     *
     * @param tbItem   商品
     * @param itemDesc 商品描述
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertItem(TbItem tbItem, TbItemDesc itemDesc, TbItemParamItem tbItemParamItem) throws DaoException {
        // 新增商品
        int index = tbItemMapper.insert(tbItem);
        // 新增商品成功
        if (index == 1) {
            // 新增商品描述
            index = tbItemDescMapper.insert(itemDesc);
            if (index==1){
                index = tbItemParamItemMapper.insertSelective(tbItemParamItem);
                if (index==1) {
                    return 1;
                }
            }

        }
        throw new DaoException("新增失败");
    }

    @Override
    public int updateItemByItem(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) throws Exception {

        // 更新 必须是动态sql，因为不一定是所有属性都有值

        int itemIndex = tbItemMapper.updateByPrimaryKeySelective(tbItem);
        if (itemIndex == 1) {
            int descIndex = tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
            if (descIndex == 1) {
                int paramIndex = tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParamItem);
                if (paramIndex==1) {
                    return 1;
                }
            }
        }
        throw new DaoException("更新失败");
    }

    @Override
    public List<TbItem> selectList() {
        return tbItemMapper.selectByExample(null);
    }

    @Override
    public TbItem getById(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }
}
