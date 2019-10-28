package com.ego.commons.pojo;

import java.util.List;

/**
 * EasyUI DataGrid返回数据封装
 * @author pengyu
 * @date 2019/9/20 16:01.
 */
public class EasyUIDataGrid {

    /**
     * 数据
     */
    private List<?> rows;
    /**
     * 总记录数
     */
    private long total;

    public EasyUIDataGrid() {
    }

    public EasyUIDataGrid(List<?> rows, long total) {
        this.rows = rows;
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
