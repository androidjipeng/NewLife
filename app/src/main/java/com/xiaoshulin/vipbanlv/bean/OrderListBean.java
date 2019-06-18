package com.xiaoshulin.vipbanlv.bean;

import java.util.List;

/**
 * Created by jipeng on 2018/5/19.
 */

public class OrderListBean {

    /**
     * returncode : 0
     * list : [{"orderid":"52644032047","name":"QQ音乐18天试用期","money":"4.89","wechat":"xiaoshulinapp","dayend":"2018-06-03","username":"使用QQ三方登录：2974583096","password":"vipbanlv.com1234","img":"http://www.vipbanlv.com/upload/201609/12/czkwh20160912221303.png","pushuid":"1","pushwechat":"xiaoshulinapp"},{"orderid":"52644025672","name":"QQ音乐18天试用期","money":"4.89","wechat":"xiaoshulinapp","dayend":"2018-06-03","username":"使用QQ三方登录：2974583096","password":"vipbanlv.com1234","img":"http://www.vipbanlv.com/upload/201609/12/czkwh20160912221303.png","pushuid":"1","pushwechat":"xiaoshulinapp"},{"orderid":"52593346753","name":"优酷视频23天试用期","money":"6.13","wechat":"xiaoshulinapp","dayend":"2018-06-02","username":"17721010424","password":"vipbanlv.com9513","img":"http://www.vipbanlv.com/www/assets/dist/img/youku_thumbnail.png","pushuid":"1374","pushwechat":"xiaoshulinapp"},{"orderid":"52587308874","name":"乐视视频24天试用期","money":"6.38","wechat":"xiaoshulinapp","dayend":"2018-06-02","username":"QQ三方登录：2797268470","password":"vipbanlv.com1358","img":"http://www.vipbanlv.com/www/assets/dist/img/letv_thumbnail.png","pushuid":"1374","pushwechat":"xiaoshulinapp"},{"orderid":"52587252827","name":"QQ音乐25天试用期","money":"6.63","wechat":"xiaoshulinapp","dayend":"2018-06-03","username":"使用QQ三方登录：2974583096","password":"vipbanlv.com1234","img":"http://www.vipbanlv.com/upload/201609/12/czkwh20160912221303.png","pushuid":"1374","pushwechat":"xiaoshulinapp"},{"orderid":"52584867871","name":"QQ音乐25天试用期","money":"6.63","wechat":"xiaoshulinapp","dayend":"2018-06-03","username":"使用QQ三方登录：2974583096","password":"vipbanlv.com1234","img":"http://www.vipbanlv.com/upload/201609/12/czkwh20160912221303.png","pushuid":"1374","pushwechat":"xiaoshulinapp"},{"orderid":"52570176235","name":"腾讯视频24天试用期","money":"6.38","wechat":"xiaoshulinapp","dayend":"2018-05-31","username":"请使用第三方QQ登陆：3184948335","password":"vipbanlv.com5084","img":"http://www.vipbanlv.com/www/assets/dist/img/tencent_thumbnail.png","pushuid":"1377","pushwechat":"ysvipzk"},{"orderid":"52570167969","name":"爱奇艺视频8天试用期","money":"2.39","wechat":"xiaoshulinapp","dayend":"已过期","username":"******","password":"******","img":"http://www.vipbanlv.com/www/assets/dist/img/iqiyi_thumbnail.png","pushuid":"1374","pushwechat":"xiaoshulinapp"},{"orderid":"52565309570","name":"腾讯视频24天试用期","money":"6.38","wechat":"xiaoshulinapp","dayend":"2018-05-31","username":"请使用第三方QQ登陆：3184948335","password":"vipbanlv.com5084","img":"http://www.vipbanlv.com/www/assets/dist/img/tencent_thumbnail.png","pushuid":"1377","pushwechat":"ysvipzk"},{"orderid":"52542771437","name":"蚂蚁森林10天试用期","money":"2.89","wechat":"xiaoshulinapp","dayend":"已过期","username":"******","password":"******","img":"http://www.vipbanlv.com/upload/201802/01/rcyem20180201133103.png","pushuid":"1","pushwechat":"xiaoshulinapp"},{"orderid":"52542770756","name":"蚂蚁森林10天试用期","money":"2.89","wechat":"xiaoshulinapp","dayend":"已过期","username":"******","password":"******","img":"http://www.vipbanlv.com/upload/201802/01/rcyem20180201133103.png","pushuid":"1","pushwechat":"xiaoshulinapp"}]
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
         * orderid : 52644032047
         * name : QQ音乐18天试用期
         * money : 4.89
         * wechat : xiaoshulinapp
         * dayend : 2018-06-03
         * username : 使用QQ三方登录：2974583096
         * password : vipbanlv.com1234
         * img : http://www.vipbanlv.com/upload/201609/12/czkwh20160912221303.png
         * pushuid : 1
         * pushwechat : xiaoshulinapp
         */

        private String orderid;
        private String name;
        private String money;
        private String wechat;
        private String dayend;
        private String username;
        private String password;
        private String img;
        private String pushuid;
        private String pushwechat;

        public String getOrderid() {
            return orderid;
        }

        public void setOrderid(String orderid) {
            this.orderid = orderid;
        }

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

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public String getDayend() {
            return dayend;
        }

        public void setDayend(String dayend) {
            this.dayend = dayend;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getPushuid() {
            return pushuid;
        }

        public void setPushuid(String pushuid) {
            this.pushuid = pushuid;
        }

        public String getPushwechat() {
            return pushwechat;
        }

        public void setPushwechat(String pushwechat) {
            this.pushwechat = pushwechat;
        }

    }
}
