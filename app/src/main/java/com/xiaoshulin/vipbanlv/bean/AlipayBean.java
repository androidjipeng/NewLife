package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;

/**
 * Created by jipeng on 2018/6/13.
 */

public class AlipayBean implements Serializable{

    /**
     * orderId : 52887060348
     * price : 4.14
     * title : 蚂蚁森林绿色能量15天试用期
     */

    private long orderId;
    private double price;
    private String title;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
