package com.xz.list.entity;

import java.io.Serializable;

/**
 * Created by xz on 2017/7/20 0020.
 * 处理图文混排的
 */

public class ItemDetailEntity implements Serializable{
    private String xContent;//内容
    private boolean isShowImg;//是否显示图片
    private int xPosition;//item下标
    private String xImageUrl;//图片路径


    public String getxContent() {
        return xContent ;
    }

    public void setxContent(String xContent) {
        this.xContent = xContent;
    }

    public boolean isShowImg() {
        return isShowImg;
    }

    public void setShowImg(boolean showImg) {
        isShowImg = showImg;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public String getxImageUrl() {
        return xImageUrl;
    }

    public void setxImageUrl(String xImageUrl) {
        this.xImageUrl = xImageUrl;
    }
}
