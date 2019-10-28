package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/26 10:00.
 */
@Service
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<TbContentCategory> selectByPid(Long pid) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        // 查询未删除且pid为pid的类目
        example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        return tbContentCategoryMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TbContentCategory insert(TbContentCategory tbContentCategory) throws DaoException {

        // 查询父节点
        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
        // 如果父节点是叶子节点，则设置父节点isParented为true
        if (!parent.getIsParent()){
            TbContentCategory updateIsParent = new TbContentCategory();
            updateIsParent.setId(parent.getId());
            updateIsParent.setIsParent(true);
            // 更新时间
            updateIsParent.setUpdated(tbContentCategory.getUpdated());
            int update = tbContentCategoryMapper.updateByPrimaryKeySelective(updateIsParent);
            if (update!=1){
                throw new DaoException("更新父节点失败");
            }
        }
        int index = tbContentCategoryMapper.insertAndReturnId(tbContentCategory);
        if (index==1){
            return tbContentCategory;
        }
        return null;
    }

    @Override
    public int updateByPk(TbContentCategory tbContentCategory) {
        return tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
    }

    @Override
    public TbContentCategory selectByPk(Long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andIdEqualTo(id).andStatusEqualTo(1);
        List<TbContentCategory> list =  tbContentCategoryMapper.selectByExample(example);
        if (list.size()==1){
            return list.get(0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAndParent(TbContentCategory tbContentCategory) throws DaoException{

        int updateIndex = tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        if (updateIndex==1){
            // 判断删除节点父节点是否还有其他子节点
            // 查询当前节点信息
            TbContentCategory current = tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getId());
            // 父节点id
            Long parentId = current.getParentId();
            TbContentCategoryExample selectParent = new TbContentCategoryExample();
            selectParent.createCriteria().andIdEqualTo(parentId).andStatusEqualTo(1);
            // 父节点所有子节点
            List<TbContentCategory> children = tbContentCategoryMapper.selectByExample(selectParent);
            if (children.size()==0){
                // 修改父节点idParented属性
                TbContentCategory updateParent = new TbContentCategory();
                updateParent.setId(parentId);
                updateParent.setUpdated(tbContentCategory.getUpdated());
                updateParent.setIsParent(false);
                int updateResult = tbContentCategoryMapper.updateByPrimaryKeySelective(updateParent);
                if (updateResult==1){
                    return 1;
                }
                throw new DaoException("更新父节点失败");
            }
            return 1;
        }

        throw new DaoException("删除节点失败");
    }


}
