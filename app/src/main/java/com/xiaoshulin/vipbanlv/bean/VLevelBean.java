package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jipeng on 2018/9/26.
 */

public class VLevelBean implements Serializable{

    /**
     * returncode : 0
     * list : [{"name":"爱奇艺视频","v":110}]
     * vcount : 110
     */

    private int returncode;
    private String vcount;
    private List<ListBean> list;

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getVcount() {
        return vcount;
    }

    public void setVcount(String vcount) {
        this.vcount = vcount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 爱奇艺视频
         * v : 110
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
