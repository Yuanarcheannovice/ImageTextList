package com.xz.list.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xz on 2017/7/20 0020.
 */

public class ItemEntity implements Serializable{
    private int id;
    private String imageUrl;
    private String title;
    private String content;
    private String addTiem;

    private List<ItemDetailEntity> listIDE;


    public List<ItemDetailEntity> getListIDE() {
        return listIDE;
    }

    public void setListIDE(List<ItemDetailEntity> listIDE) {
        this.listIDE = listIDE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddTiem() {
        return addTiem;
    }

    public void setAddTiem(String addTiem) {
        this.addTiem = addTiem;
    }
}
