package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;

/**
 * Created by jipeng on 2019/4/15.
 */
public class ParseBean implements Serializable {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
