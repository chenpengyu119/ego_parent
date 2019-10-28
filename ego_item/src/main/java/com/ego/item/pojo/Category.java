package com.ego.item.pojo;

import java.util.List;

/**
 * 门户中导航的每一层
 * @author pengyu
 * @date 2019/9/27 15:37.
 */
public class Category {

    private String u;
    private String n;
    /**
     *  i中的数据可能是对象或者String
     */
    private List<?> i;

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public List<?> getI() {
        return i;
    }

    public void setI(List<?> i) {
        this.i = i;
    }
}
