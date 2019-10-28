package com.ego.portal.pojo;

/**
 * 首页大广告
 * @author pengyu
 * @date 2019/9/28 16:10.
 */
public class PortalAd {

    private String srcB;
    private int height;
    private int width;
    private String src;
    private int widthB;
    /**
     * 点击图片跳转的url
     */
    private String href;
    private int heightB;
    private String alt;

    @Override
    public String toString() {
        return "PortalAd{" +
                "srcB='" + srcB + '\'' +
                ", height=" + height +
                ", width=" + width +
                ", src='" + src + '\'' +
                ", widthB=" + widthB +
                ", href='" + href + '\'' +
                ", heightB=" + heightB +
                ", alt='" + alt + '\'' +
                '}';
    }

    public String getSrcB() {
        return srcB;
    }

    public void setSrcB(String srcB) {
        this.srcB = srcB;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getWidthB() {
        return widthB;
    }

    public void setWidthB(int widthB) {
        this.widthB = widthB;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getHeightB() {
        return heightB;
    }

    public void setHeightB(int heightB) {
        this.heightB = heightB;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
