package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;

/**
 * JPushBean
 *
 * @author jipeng
 * @date 2018/8/8
 */
public class JPushBean implements Serializable{
    String content;
    String pushtype;
    String customfield;
    String ALERT;
//    String ID;
    String ALERT_TYPE;
    String NOTIFICATION_CONTENT_TITLE;
    String MSG_ID;


//    public String getID() {
//        return ID;
//    }
//
//    public void setID(String ID) {
//        this.ID = ID;
//    }

    public String getALERT_TYPE() {
        return ALERT_TYPE;
    }

    public void setALERT_TYPE(String ALERT_TYPE) {
        this.ALERT_TYPE = ALERT_TYPE;
    }

    public String getNOTIFICATION_CONTENT_TITLE() {
        return NOTIFICATION_CONTENT_TITLE;
    }

    public void setNOTIFICATION_CONTENT_TITLE(String NOTIFICATION_CONTENT_TITLE) {
        this.NOTIFICATION_CONTENT_TITLE = NOTIFICATION_CONTENT_TITLE;
    }

    public String getMSG_ID() {
        return MSG_ID;
    }

    public void setMSG_ID(String MSG_ID) {
        this.MSG_ID = MSG_ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCustomfield() {
        return customfield;
    }

    public void setCustomfield(String customfield) {
        this.customfield = customfield;
    }

    public String getALERT() {
        return ALERT;
    }

    public void setALERT(String ALERT) {
        this.ALERT = ALERT;
    }

    public String getPushtype() {
        return pushtype;
    }

    public void setPushtype(String pushtype) {
        this.pushtype = pushtype;
    }
    @Override
    public String toString() {
        return "JPushBean{" +
                "content='" + content + '\'' +
                ", pushtype='" + pushtype + '\'' +
                ", customfield='" + customfield + '\'' +
                ", ALERT='" + ALERT + '\'' +
                ", ALERT_TYPE='" + ALERT_TYPE + '\'' +
                ", NOTIFICATION_CONTENT_TITLE='" + NOTIFICATION_CONTENT_TITLE + '\'' +
                ", MSG_ID='" + MSG_ID + '\'' +
                '}';
    }
}
