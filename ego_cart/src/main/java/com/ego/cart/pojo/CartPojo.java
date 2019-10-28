package com.ego.cart.pojo;

import com.ego.pojo.TbItem;

/**
 * @author pengyu
 * @date 2019/10/10 16:44.
 */
public class CartPojo extends TbItem {
    private String[] images;

    /**
     * 库存是否足够
     */
    private Boolean enough;

    public Boolean getEnough() {
        return enough;
    }

    public void setEnough(Boolean enough) {
        this.enough = enough;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
