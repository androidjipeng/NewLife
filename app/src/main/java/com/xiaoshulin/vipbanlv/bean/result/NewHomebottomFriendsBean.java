package com.xiaoshulin.vipbanlv.bean.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jipeng on 2019-10-21 16:57.
 */
public class NewHomebottomFriendsBean implements Serializable {

    /**
     * returncode : 0
     * message : 成功
     * vbottomtime : 2019-06-16 12:03:39
     * bottomlist : [{"title":"秘密圈","list":[{"title":"商务合伙人","url":"https://mp.weixin.qq.com/s/9FkHYYySzggzBuTXJACxjQ"},{"title":"19元得22V币","url":"http*888**bf5acccc@t,9$u,351$i,小树林APP*888**"},{"title":"他眼中的你","url":"http*888**d4689f16@t,8$t1,0$u,http://mp.weixin.qq.com/s/***ACCameraController***$i,这个相机中的你是左右颠倒的，也正好是别人面对面看到你的样子*888**"},{"title":"手电筒闪屏","url":"http://www.vipbanlv.com"},{"title":"一号红包日","url":"https://mp.weixin.qq.com/s/Pd02bAWzAVDNih7IyKlU6A"}]},{"title":"福利社","list":[{"title":"每日福利","url":"https://mp.weixin.qq.com/s/CNLWNj9WtZCanWcZq87CUg"},{"title":"男模靓照","url":"http://dwz.cn/MfX7zWWz"},{"title":"美女图片","url":"https://dwz.cn/gQ7UjbfS"},{"title":"投稿给大家看","url":"http*888**a203e922@t,8$t1,0$u,http*4**weixinkefuxiaoshulinapp*4**$i,我要投稿，我发现一个不错视频、文章、知识想分享给大家，请添加微信客服微信号：xiaoshulinapp*888**"},{"title":"进一号红包群","url":"http*888**b1ee779e@t,8$t1,0$u,http*4**weixinkefuxiaoshulinapp*4**$i,进一号红包群，请添加微信客服微信号：xiaoshulinapp*888**"}]}]
     */

    private int returncode;
    private String message;
    private String vbottomtime;
    private List<BottomlistBean> bottomlist;

    public int getReturncode() {
        return returncode;
    }

    public void setReturncode(int returncode) {
        this.returncode = returncode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVbottomtime() {
        return vbottomtime;
    }

    public void setVbottomtime(String vbottomtime) {
        this.vbottomtime = vbottomtime;
    }

    public List<BottomlistBean> getBottomlist() {
        return bottomlist;
    }

    public void setBottomlist(List<BottomlistBean> bottomlist) {
        this.bottomlist = bottomlist;
    }

    public static class BottomlistBean {
        /**
         * title : 秘密圈
         * list : [{"title":"商务合伙人","url":"https://mp.weixin.qq.com/s/9FkHYYySzggzBuTXJACxjQ"},{"title":"19元得22V币","url":"http*888**bf5acccc@t,9$u,351$i,小树林APP*888**"},{"title":"他眼中的你","url":"http*888**d4689f16@t,8$t1,0$u,http://mp.weixin.qq.com/s/***ACCameraController***$i,这个相机中的你是左右颠倒的，也正好是别人面对面看到你的样子*888**"},{"title":"手电筒闪屏","url":"http://www.vipbanlv.com"},{"title":"一号红包日","url":"https://mp.weixin.qq.com/s/Pd02bAWzAVDNih7IyKlU6A"}]
         */

        private String title;
        private List<ListBean> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * title : 商务合伙人
             * url : https://mp.weixin.qq.com/s/9FkHYYySzggzBuTXJACxjQ
             */

            private String title;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
