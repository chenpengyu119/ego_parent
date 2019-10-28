package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemDescExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/9/23 17:07.
 */
@Service
public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public TbItemDesc selectById(Long id) {
        TbItemDescExample example = new TbItemDescExample();
        example.createCriteria().andItemIdEqualTo(id);
        List<TbItemDesc> list = tbItemDescMapper.selectByExampleWithBLOBs(example);
        if (list!=null && list.size() == 1){
            return list.get(0);
        }
        return null;

    }
}
