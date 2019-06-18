package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;

/**
 * Created by jipeng on 2019/2/20.
 */
public class CricleBuyPro implements Serializable {

    /**
     * returncode : 0
     * message : 爱生活！爱自己！
     * orderid : 55065274666
     * price : 0.80
     * title : 购买后可以得到一个十人份的2元支付宝口令红包，可以自己领取也可以分享给好友一起领取哦！每人限购一份！
     * paytype : 1
     */

    private String returncode;
    private String message;
    private String orderid;
    private String price;
    private String title;
    private String paytype;

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

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }
}
