package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/24 14:04.
 */
@Service
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {

    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public List<TbItemParam> selectByPage(int pageNum, int pageSize) {

        // 设置分页信息
        PageHelper.startPage(pageNum, pageSize);
        // 查询全部
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(null);
        // 设置数据
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
        return list;
    }

    @Override
    public TbItemParam selectByItemCatId(Long itemCatId) {

        TbItemParamExample example = new TbItemParamExample();
        // 设置商品类目id
        example.createCriteria().andItemCatIdEqualTo(itemCatId);
        // 根据类目查询规格信息
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(example);
        // 因为一个类目只能对应一个规格参数，所以需要取第一个
        if (tbItemParams!=null && tbItemParams.size()==1){
            return tbItemParams.get(0);
        }
        // 如果没有查到也返回null
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbItemParam tbItemParam) throws DaoException{

        int index = tbItemParamMapper.insertSelective(tbItemParam);
        if (index==1){
            return 1;
        }
        throw new DaoException("新增失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByIds(Long[] ids) throws DaoException{

        int index = 0;
        for (Long id : ids){
            index += tbItemParamMapper.deleteByPrimaryKey(id);
        }
        if (index==ids.length){
            return 1;
        }
        throw new DaoException("删除失败");
    }
}
