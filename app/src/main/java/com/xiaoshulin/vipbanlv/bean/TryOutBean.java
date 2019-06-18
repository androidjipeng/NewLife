package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * TryOutBean
 *
 * @author jipeng
 * @date 2018/9/29
 */
public class TryOutBean implements Serializable {


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
        private String count;

        private String pid;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
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
