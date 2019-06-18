package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;
import java.util.List;

public class danMuBean implements Serializable {

    /**
     * returncode : 0
     * message :
     * data : [{"describe":"点击这条消息支付宝天天领红包啦！","url":"http://www.vipbanlv.com/www/#!/custom-price"},{"describe":"男人专区1","url":"http://dwz.cn/RRy1URcJ"},{"describe":"女人专区1","url":"http://dwz.cn/MfX7zWWz"},{"describe":"罗永浩演讲合集搞笑不失真知","url":"https://m.ximalaya.com/share/sound/36532298"},{"describe":"你想要的都在这里","url":"http://m.banlvnihao.icoc.cc/nd.jsp?id=11"},{"describe":"男人专区2","url":"http://m.ugirls.com"},{"describe":"女人专区2","url":"http://dwz.cn/0PzUfwnk"},{"describe":"笑的我肚子疼","url":"http://url.cn/5ckD9vj"},{"describe":"小美女又可爱又漂亮","url":"http://url.cn/5Iytnnz"},{"describe":"小树林APP精选福利","url":"http://m.banlvnihao.icoc.cc/nd.jsp?id=11"},{"describe":"添加小树林支付宝客服好友进红包群","url":"http://mp.weixin.qq.com/s/*4**zhifubaokefu#吱口令#长按复制此条消息，打开支付宝即可去小树林APP支付宝客服反馈01MmLE56xb*4**"},{"describe":"安慰剂效应对你有用","url":"https://m.igetget.com/share/course/article/alias_id/qRlGF8Kk5pox41apd2wv"},{"describe":"直播大聚合女神、男神、游戏样样俱全","url":"http://dwz.cn/EuUvhs5K?*8*8*8*"}]
     */

    private String returncode;
    private String message;
    private List<DataBean> data;

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * describe : 点击这条消息支付宝天天领红包啦！
         * url : http://www.vipbanlv.com/www/#!/custom-price
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
