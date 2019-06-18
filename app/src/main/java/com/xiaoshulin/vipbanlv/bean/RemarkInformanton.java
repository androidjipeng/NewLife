package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;

/**
 * Created by jipeng on 2019/2/28.
 */
public class RemarkInformanton implements Serializable {

    /**
     * returncode : 0
     * message : 成功
     * remark : 购买成功后点击左上角客服咨询获取支付宝现金红包口令。
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
