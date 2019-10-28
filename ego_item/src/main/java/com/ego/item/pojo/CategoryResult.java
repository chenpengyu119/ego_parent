package com.ego.item.pojo;

import java.util.List;

/**
 * 用来装载门户中导航菜单数据
 * @author pengyu
 * @date 2019/9/27 15:23.
 */
public class CategoryResult {

    private List<Category> data;

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
}
