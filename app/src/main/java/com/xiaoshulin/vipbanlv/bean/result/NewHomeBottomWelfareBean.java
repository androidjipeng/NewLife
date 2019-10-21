package com.xiaoshulin.vipbanlv.bean.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jipeng on 2019-10-21 16:59.
 */
public class NewHomeBottomWelfareBean implements Serializable {

    /**
     * returncode : 0
     * list : [{"describe":"保赚#200元#左右","url":"http*888**8c3deeb9@t,9$u,324$i,xiaoshulinapp*888**","title":"保底稳赚免费用","icon":"https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU0NDE2MjkzODA1OV9hNjBmNGQ3Ny1kMWVjLTRjNDktYTYyNC1iM2M3YjNiYjQwN2Q=","createdate":"2019-03-03 18:07:54"},{"describe":"仅限新用户","url":"http*888**a4192064@t,9$u,348$i,xiaoshulinapp*888**","title":"#1分钱#用会员账号","icon":"https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU0NDE2MjkzODA1OV9kNDAxNmM5OS0yZDYwLTRkYzYtOGExMi0yZmYzMTVkMDdkODY=","createdate":"2019-03-03 18:08:01"},{"describe":"每人限购一次","url":"http*888**92f94e19@t,9$u,339$i,xiaoshulinapp*888**","title":"0.8元得2元红包","icon":"https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU0NDE2MjkzODA1OV81MGVlN2Q3NS1jZDE2LTRlZTktODc0NS1hNzlkNDZjOWJkNzc=","createdate":"2019-03-03 18:08:05"},{"describe":"可以无限累积时间","url":"http*888**d01baca8@t,9$u,321$i,xiaoshulinapp*888**","title":"150克绿色能量","icon":"https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU0NDE2MjkzODA1OV9lNmQyNWIxYi1kMDFkLTRkMmUtOWY2NS00NjIzMmE3MzQyZWI=","createdate":"2019-03-03 18:08:10"},{"describe":"30个可偷绿色能量大户","url":"http*888**4a420b30@t,9$u,322$i,xiaoshulinapp*888**","title":"蚂蚁森林大神账号","icon":"https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU0NDE2MjkzODA1OV9lNmQyNWIxYi1kMDFkLTRkMmUtOWY2NS00NjIzMmE3MzQyZWI=","createdate":"2019-03-03 18:08:15"},{"describe":"#聚实惠#每月才6元","url":"http*888**cba88759@t,9$u,432*888**","title":"18元得三个会员","icon":"https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1ODUxMTYwMDYyOF9kNDFhZmY0NS0yNzYyLTRjZTYtYmJmNi05ZjBlYTQxNDc0YTM=","createdate":"2019-05-23 21:51:49"},{"describe":"秀色可餐--想入非非","url":"http*888**6f8ef4d1@t,9$u,334$i,xiaoshulinapp*888**","title":"一百张美女壁纸","icon":"https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU0NDE2MjkzODA1OV85YmVhZDkwMC00YmVjLTQ5NDQtODJiZi02ODA5YzQ0OWNhMGQ=","createdate":"2019-03-03 18:08:20"},{"describe":"每周五晚更新","url":"http*888**9c6abf10@t,9$u,352*888**","title":"美女视频","icon":"https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU0NDE2MjkzODA1OV82Y2E3ZjRjZC04MzQ3LTQ4NjQtYTYyZi1hZDdhNmYxMTg4NTM=","createdate":"2019-03-03 18:08:37"},{"describe":"来赚钱吧！","url":"http*888**cf13c1e4@t,9$u,323$i,xiaoshulinapp*888**","title":"发布商品模板","icon":"https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MTQwNzM5NDA2MV9lMTFiOWNhMi04Y2ExLTQwMTktOTk0Zi1jMTAxZTI4NWMyZmU=","createdate":"2019-03-03 22:44:51"}]
     * freevideotime : 2019-05-23 21:51:49
     */

    private String returncode;
    private String freevideotime;
    private List<ListBean> list;

    public String getReturncode() {
        return returncode;
    }

    public void setReturncode(String returncode) {
        this.returncode = returncode;
    }

    public String getFreevideotime() {
        return freevideotime;
    }

    public void setFreevideotime(String freevideotime) {
        this.freevideotime = freevideotime;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * describe : 保赚#200元#左右
         * url : http*888**8c3deeb9@t,9$u,324$i,xiaoshulinapp*888**
         * title : 保底稳赚免费用
         * icon : https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU0NDE2MjkzODA1OV9hNjBmNGQ3Ny1kMWVjLTRjNDktYTYyNC1iM2M3YjNiYjQwN2Q=
         * createdate : 2019-03-03 18:07:54
         */

        private String describe;
        private String url;
        private String title;
        private String icon;
        private String createdate;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getCreatedate() {
            return createdate;
        }

        public void setCreatedate(String createdate) {
            this.createdate = createdate;
        }
    }
}
