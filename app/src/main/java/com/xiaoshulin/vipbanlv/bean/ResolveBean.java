package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;

/**
 * Created by jipeng on 2018/12/6.
 */
public class ResolveBean implements Serializable {
    int tag;
    String content;
    String shareuid;
    String url;
    String id;

    public ResolveBean(int tag, String content, String shareuid, String url, String id) {
        this.tag = tag;
        this.content = content;
        this.shareuid = shareuid;
        this.url = url;
        this.id = id;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShareuid() {
        return shareuid;
    }

    public void setShareuid(String shareuid) {
        this.shareuid = shareuid;
    }
}
