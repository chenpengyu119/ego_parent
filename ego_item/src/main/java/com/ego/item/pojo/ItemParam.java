package com.ego.item.pojo;

import java.util.List;

/**
 * @author pengyu
 * @date 2019/10/8 17:33.
 */
public class ItemParam {

    private String group;
    private List<ItemParamKeyAndValue> params;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<ItemParamKeyAndValue> getParams() {
        return params;
    }

    public void setParams(List<ItemParamKeyAndValue> params) {
        this.params = params;
    }
}
