package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jipeng on 2018/12/26.
 */
public class CircleAdvertisementBean implements Serializable {

    /**
     * returncode : 0
     * message :
     * data : [{"generalizeimage":"http://www.vipbanlv.com/upload/201812/19/qvcid20181219150501.png","generalizeurl":"http://www.vipbanlv.com"}]
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

    public static class DataBean {
        /**
         * generalizeimage : http://www.vipbanlv.com/upload/201812/19/qvcid20181219150501.png
         * generalizeurl : http://www.vipbanlv.com
         */

        private String generalizeimage;
        private String generalizeurl;

        public String getGeneralizeimage() {
            return generalizeimage;
        }

        public void setGeneralizeimage(String generalizeimage) {
            this.generalizeimage = generalizeimage;
        }

        public String getGeneralizeurl() {
            return generalizeurl;
        }

        public void setGeneralizeurl(String generalizeurl) {
            this.generalizeurl = generalizeurl;
        }
    }
}
