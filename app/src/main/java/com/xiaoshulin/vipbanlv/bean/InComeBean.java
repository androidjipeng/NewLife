package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * InComeBean
 *
 * @author jipeng
 * @date 2018/9/26
 */
public class InComeBean implements Serializable{

    /**
     * returncode : 0
     * list : [{"name":"111111转让","money":"110.00"}]
     * money : 110
     */

    private int returncode;
    private String money;
    private List<ListBean> list;

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 111111转让
         * money : 110.00
         */

        private String name;
        private String money;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
