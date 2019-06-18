package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jipeng on 2019/1/9.
 */
public class CricleCenterBean implements Serializable {

    /**
     * returncode : 0
     * circle : {"circleid":"324","circleuid":"1374","avatar":"temporary11","name":"用户ID：1374","content":"保底稳赚：小树林官方《腾讯视频》《爱奇艺》《优酷》《芒果TV》黄金会员给自己账号充值服务开启了，所有平台一年都只需要129元，比官网便宜的多哦，给自己账号充值后可自用，也可以来小树林赚钱，我们保证您可以免费用一年还可以赚200左右，不赚钱退钱给您(ps必须是在我们平台充值的账号哦)，想想都美美哒。","imgs":["http://www.vipbanlv.com/upload/201810/20/xiaft20181020100818.jpg"],"thumbnails":["http://www.vipbanlv.com/upload/201810/20/xiaft20181020100818.jpg"],"hyperlinkimage":"Icon","hyperlinkdescribe":"点我！点我！小树林里可以购买各种东东，您的每一笔交易都享受担保。","hyperlinkurl":"http://www.vipbanlv.com","assuredate":"365","price":"129.00","date":"2018-12-19 01:56:05","iscollect":"0","isstick":"0","islike":"0","likelist":["1374"],"commentlist":[]}
     */

    private String returncode;
    private CircleBean circle;

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public CircleBean getCircle() {
        return circle;
    }

    public void setCircle(CircleBean circle) {
        this.circle = circle;
    }

    public static class CircleBean {
        /**
         * circleid : 324
         * circleuid : 1374
         * avatar : temporary11
         * name : 用户ID：1374
         * content : 保底稳赚：小树林官方《腾讯视频》《爱奇艺》《优酷》《芒果TV》黄金会员给自己账号充值服务开启了，所有平台一年都只需要129元，比官网便宜的多哦，给自己账号充值后可自用，也可以来小树林赚钱，我们保证您可以免费用一年还可以赚200左右，不赚钱退钱给您(ps必须是在我们平台充值的账号哦)，想想都美美哒。
         * imgs : ["http://www.vipbanlv.com/upload/201810/20/xiaft20181020100818.jpg"]
         * thumbnails : ["http://www.vipbanlv.com/upload/201810/20/xiaft20181020100818.jpg"]
         * hyperlinkimage : Icon
         * hyperlinkdescribe : 点我！点我！小树林里可以购买各种东东，您的每一笔交易都享受担保。
         * hyperlinkurl : http://www.vipbanlv.com
         * assuredate : 365
         * price : 129.00
         * date : 2018-12-19 01:56:05
         * iscollect : 0
         * isstick : 0
         * islike : 0
         * likelist : ["1374"]
         * commentlist : []
         */

        private String circleid;
        private String circleuid;
        private String avatar;
        private String name;
        private String content;
        private String hyperlinkimage;
        private String hyperlinkdescribe;
        private String hyperlinkurl;
        private String assuredate;
        private String price;
        private String date;
        private String iscollect;
        private String isstick;
        private String islike;
        private List<String> imgs;
        private List<String> thumbnails;
        private List<String> likelist;
        private List<?> commentlist;

        public String getCircleid() {
            return circleid;
        }

        public void setCircleid(String circleid) {
            this.circleid = circleid;
        }

        public String getCircleuid() {
            return circleuid;
        }

        public void setCircleuid(String circleuid) {
            this.circleuid = circleuid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHyperlinkimage() {
            return hyperlinkimage;
        }

        public void setHyperlinkimage(String hyperlinkimage) {
            this.hyperlinkimage = hyperlinkimage;
        }

        public String getHyperlinkdescribe() {
            return hyperlinkdescribe;
        }

        public void setHyperlinkdescribe(String hyperlinkdescribe) {
            this.hyperlinkdescribe = hyperlinkdescribe;
        }

        public String getHyperlinkurl() {
            return hyperlinkurl;
        }

        public void setHyperlinkurl(String hyperlinkurl) {
            this.hyperlinkurl = hyperlinkurl;
        }

        public String getAssuredate() {
            return assuredate;
        }

        public void setAssuredate(String assuredate) {
            this.assuredate = assuredate;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getIscollect() {
            return iscollect;
        }

        public void setIscollect(String iscollect) {
            this.iscollect = iscollect;
        }

        public String getIsstick() {
            return isstick;
        }

        public void setIsstick(String isstick) {
            this.isstick = isstick;
        }

        public String getIslike() {
            return islike;
        }

        public void setIslike(String islike) {
            this.islike = islike;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public List<String> getThumbnails() {
            return thumbnails;
        }

        public void setThumbnails(List<String> thumbnails) {
            this.thumbnails = thumbnails;
        }

        public List<String> getLikelist() {
            return likelist;
        }

        public void setLikelist(List<String> likelist) {
            this.likelist = likelist;
        }

        public List<?> getCommentlist() {
            return commentlist;
        }

        public void setCommentlist(List<?> commentlist) {
            this.commentlist = commentlist;
        }
    }
}
