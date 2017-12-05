package com.chenzicong.weichatclong.beans;

import java.io.Serializable;

/**
 * Created by ChenZiCong on 2017/11/29.
 */

public class RV_centent_bean implements Serializable{
    private String url ;
    private String title;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
