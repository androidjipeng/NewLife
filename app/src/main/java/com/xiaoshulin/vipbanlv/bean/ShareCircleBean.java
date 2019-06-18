package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;

/**
 * Created by jipeng on 2018/11/17.
 */

public class ShareCircleBean implements Serializable {

    /**
     * returncode : 0
     * message : 成功
     * remark : http%3A%2F%2Fwww.baidu.com
     */

    private String returncode;
    private String message;
    private String remark;

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
