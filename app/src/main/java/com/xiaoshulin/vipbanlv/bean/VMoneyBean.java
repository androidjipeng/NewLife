package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jipeng on 2018/9/26.
 */

public class VMoneyBean implements Serializable{

    /**
     * returncode : 0
     * list : [{"name":"购买包年服务获得V币0.01，爱奇艺会员","v":"+0.01"},{"name":"购买包年服务获得V币0.01，激活码","v":"+0.01"},{"name":"购买包年服务获得V币0.01，激活码","v":"+0.01"}]
     * vmoneycount : 73.659999999999
     * rechargeurl : http://www.vipbanlv.com
     */

    private int returncode;
    private String vmoneycount;
    private String rechargeurl;
    private List<ListBean> list;

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getVmoneycount() {
        return vmoneycount;
    }

    public void setVmoneycount(String vmoneycount) {
        this.vmoneycount = vmoneycount;
    }

    public String getRechargeurl() {
        return rechargeurl;
    }

    public void setRechargeurl(String rechargeurl) {
        this.rechargeurl = rechargeurl;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 购买包年服务获得V币0.01，爱奇艺会员
         * v : +0.01
         */

        private String name;
        private String v;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getV() {
            return v;
        }

        public void setV(String v) {
            this.v = v;
        }
    }
}
