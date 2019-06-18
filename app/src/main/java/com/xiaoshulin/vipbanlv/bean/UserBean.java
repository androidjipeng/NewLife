package com.xiaoshulin.vipbanlv.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jipeng on 2018/9/24.
 */

public class UserBean {

    /**
     * returncode : 0
     * v : 110
     * money : 110
     * income : 110
     * username : james
     */

    private int returncode;
    private float v;
    @SerializedName("v-money")
    String vmoney;



    private float money;
    private float income;
    private String username;
    private String alipay;
    private int usercount;

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public int getUsercount() {
        return usercount;
    }

    public void setUsercount(int usercount) {
        this.usercount = usercount;
    }

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getIncome() {
        return income;
    }


    public String getVmoney() {
        return vmoney;
    }

    public void setVmoney(String vmoney) {
        this.vmoney = vmoney;
    }
    public void setIncome(float income) {
        this.income = income;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
