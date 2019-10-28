package com.ego.search.pojo;

import org.apache.solr.client.solrj.beans.Field;

import java.util.Arrays;

/**
 * @author pengyu
 * @date 2019/9/29 14:56.
 */
public class SolrEntity {


    @Field("id")
    private Long id;
    @Field("item_title")
    private String title;
    @Field("item_sell_point")
    private String sellPoint;
    /**
     * 单位：分
     */
    @Field("item_price")
    private Long price;
    @Field("item_image")
    private String image;
    private String[] images;
    @Field("item_category_name")
    private String item_category_name;
    @Field("item_desc")
    private String item_desc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getItem_category_name() {
        return item_category_name;
    }

    public void setItem_category_name(String item_category_name) {
        this.item_category_name = item_category_name;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    @Override
    public String toString() {
        return "SolrEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", sellPoint='" + sellPoint + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", images=" + Arrays.toString(images) +
                ", item_category_name='" + item_category_name + '\'' +
                ", item_desc='" + item_desc + '\'' +
                '}';
    }
}
