package com.ego.service.impl;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.pojo.TbItemDesc;
import com.ego.service.TbItemDescService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pengyu
 * @date 2019/9/23 17:12.
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Override
    public TbItemDesc getById(Long id) {
        return tbItemDescDubboService.selectById(id);
    }
}
