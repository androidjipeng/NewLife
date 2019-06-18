package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jipeng on 2018/10/10.
 */

public class UserNewsBean implements Serializable{

    /**
     * returncode : 0
     * list : [{"message":"有人购买了你的账号","time":"2016-06-10 10:24:14","status":"0","id":"1"}]
     */

    private int returncode;
    private List<ListBean> list;

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * message : 有人购买了你的账号
         * time : 2016-06-10 10:24:14
         * status : 0
         * id : 1
         */

        private String message;
        private String time;
        private String status;
        private String id;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
