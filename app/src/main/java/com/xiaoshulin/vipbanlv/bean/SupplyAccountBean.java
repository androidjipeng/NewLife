package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * SupplyAccountBean
 *
 * @author jipeng
 * @date 2018/10/22
 */
public class SupplyAccountBean implements Serializable {
    //{
    //    "returncode":0,
    //    "list":[
    //        {
    //            "img":"http://www.vipbanlv.com/www/assets/dist/img/letv_thumbnail.png",
    //            "name":"乐视视频",
    //            "needs":"4",
    //            "pid":"2"
    //        }
    //    ]
    //}


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
         * name : 爱奇艺视频
         * v : 110
         */

        private String name;
        private String img;
        private String needs;

        private String pid;

        public String getNeeds() {
            return needs;
        }

        public void setNeeds(String needs) {
            this.needs = needs;
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

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
    }
}
