package com.xiaoshulin.vipbanlv.bean.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jipeng on 2019-10-18 09:08.
 */
public class NewHomeTopAdvBean implements Serializable {

    /**
     * returncode : 0
     * list : [{"describe":"头条测试-百度","url":"http://www.baidu.com"},{"describe":"头条测试-新浪","url":"http://www.sina.com.cn"}]
     */

    private String returncode;
    private List<ListBean> list;

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
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
         * describe : 头条测试-百度
         * url : http://www.baidu.com
         */

        private String describe;
        private String url;

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
