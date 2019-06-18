package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by jp on 2018/4/14.
 */

public class MainDataBean implements Serializable {

    public MainDataBean(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url=url;
    }

    private String title;
    private String content;
    private String url;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
