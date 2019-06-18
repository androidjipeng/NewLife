package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jipeng on 2018/10/10.
 */

public class UserAccountListBean implements Serializable{

    /**
     * returncode : 0
     * list : [{"aid":"1","name":"13111111111","img":"http://www.vipbanlv.com/www/assets/dist/img/iqiyi_logo.png","endtime":"已过期"},{"aid":"5","name":"111111","img":"http://www.vipbanlv.com/www/assets/dist/img/iqiyi_logo.png","endtime":"已过期"},{"aid":"6","name":"111111","img":"http://www.vipbanlv.com/www/assets/dist/img/iqiyi_logo.png","endtime":"2016-07-10"},{"aid":"7","name":"111111","img":"http://www.vipbanlv.com/www/assets/dist/img/iqiyi_logo.png","endtime":"2016-07-10"},{"aid":"8","name":"111111","img":"http://www.vipbanlv.com/www/assets/dist/img/iqiyi_logo.png","endtime":"2016-07-10"}]
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
         * aid : 1
         * name : 13111111111
         * img : http://www.vipbanlv.com/www/assets/dist/img/iqiyi_logo.png
         * endtime : 已过期
         */

        private String aid;
        private String name;
        private String img;
        private String endtime;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }
    }
}
