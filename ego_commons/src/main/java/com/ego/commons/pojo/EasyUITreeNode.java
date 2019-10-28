package com.ego.commons.pojo;

/**
 * EasyUI的tree要求的数据结构
 * @author pengyu
 * @date 2019/9/23 15:29.
 */
public class EasyUITreeNode {
    private Long id;
    private String text;
    /**
     * 是否为打开状态
     * open 打开
     * cloed 关闭
     */
    private String state;

    @Override
    public String toString() {
        return "EasyUITreeNode{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
