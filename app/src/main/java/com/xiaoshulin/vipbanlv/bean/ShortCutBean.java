package com.xiaoshulin.vipbanlv.bean;

/**
 * Created by jipeng on 2018/11/30.
 */
public class ShortCutBean {
    private String title1;
    private String content1;

    private String title2;
    private String content2;

    public ShortCutBean(String title1, String content1, String title2, String content2) {
        this.title1 = title1;
        this.content1 = content1;
        this.title2 = title2;
        this.content2 = content2;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }
}
