package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;

/**
 * FreeWhachBean
 *
 * @author jipeng
 * @date 2018/8/1
 */
public class FreeWhachBean implements Serializable{


    /**
     * returncode : 0
     * url : https://youku.cdn-56.com/20180607/3462_39442366/index.m3u8
     */

    private String returncode;
    private String url;

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
