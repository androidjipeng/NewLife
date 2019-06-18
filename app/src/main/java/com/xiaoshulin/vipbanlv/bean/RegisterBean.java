package com.xiaoshulin.vipbanlv.bean;

/**
 * Created by jipeng on 2018/5/19.
 */

public class RegisterBean {

    /**
     * returncode : 0
     * uid : 1671
     * sign : 328537018
     */

    private int returncode;
    private String uid;
    private String sign;
    private String uidtoken;

    public String getUidtoken() {
        return uidtoken;
    }

    public void setUidtoken(String uidtoken) {
        this.uidtoken = uidtoken;
    }

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
