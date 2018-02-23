package com.custom.yumiao.customviewpractice.model;

/**
 * Created by yumiaomiao on 2018/2/23.
 */

public class MainData {

    public String title;
    public int layoutId;

    public MainData(String title, int layoutId) {
        this.title = title;
        this.layoutId = layoutId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }
}
