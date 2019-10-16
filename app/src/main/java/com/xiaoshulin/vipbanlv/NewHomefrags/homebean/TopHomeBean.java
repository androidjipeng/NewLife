package com.xiaoshulin.vipbanlv.NewHomefrags.homebean;

/**
 * Created by jipeng on 2019-10-16 15:32.
 */
public class TopHomeBean {
    String title;
    String icon;
    String url;

    public TopHomeBean(String title, String icon, String url) {
        this.title = title;
        this.icon = icon;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
