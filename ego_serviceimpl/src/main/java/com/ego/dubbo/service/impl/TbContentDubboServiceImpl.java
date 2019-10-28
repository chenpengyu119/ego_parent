package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/26 17:27.
 */
@Service
public class TbContentDubboServiceImpl implements TbContentDubboService {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public List<TbContent> selectByCategoryIdPage(Long categoryId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        TbContentExample example = new TbContentExample();
        // 查询全部数据
        if (categoryId!=0) {
            example.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);
      //  PageInfo<TbContent> pi = new PageInfo<>(list);
        return list;
    }

    @Override
    public Long selectCount(Long categoryId) {
        TbContentExample example = new TbContentExample();
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        return tbContentMapper.countByExample(example);
    }

    @Override
    public int insert(TbContent tbContent) {
        int index = tbContentMapper.insertSelective(tbContent);
        if (index == 1){
            return 1;
        }
        return 0;
    }

    @Override
    public List<TbContent> selectTopNByCategoryId(Long categoryId, int pageNum, int pageSize) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);
        TbContentExample example = new TbContentExample();
        // 查询条件
        example.createCriteria().andCategoryIdEqualTo(categoryId);
        // 降序排序
        example.setOrderByClause("updated desc");
        List<TbContent> list = tbContentMapper.selectByExample(example);
        return list;
    }

    @Override
    @Transactional(rollbackFor = DaoException.class)
    public int deleteByIds(Long[] ids) throws DaoException{
        int index = 0;
        for (Long id:ids){
            index += tbContentMapper.deleteByPrimaryKey(id);
        }
        if (index == ids.length){
            return 1;
        }
        throw new DaoException("删除分类内容失败");
    }
}
