package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/23 9:58.
 */
@Service
public class TbItemCatDubboServiceImpl  implements TbItemCatDubboService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<TbItemCat> selectByPid(Long pid) {
        TbItemCatExample example = new TbItemCatExample();
        // 查询状态未删除且pid为pid的类目
        example.createCriteria().andParentIdEqualTo(pid).andStatusEqualTo(1);
        return tbItemCatMapper.selectByExample(example);
    }

    @Override
    public TbItemCat getById(Long id) {
        return tbItemCatMapper.selectByPrimaryKey(id);
    }

}
