package com.xiaoshulin.vipbanlv.bean;

import java.io.Serializable;

/**
 * Created by jipeng on 2018/11/27.
 */
public class CostomFieldBean implements Serializable {
    private String nickName;
    private String type;
    private String icon;
    private String messageSection;
    private String time;
    private String chatId;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMessageSection() {
        return messageSection;
    }

    public void setMessageSection(String messageSection) {
        this.messageSection = messageSection;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "CostomFieldBean{" +
                "nickName='" + nickName + '\'' +
                ", type='" + type + '\'' +
                ", icon='" + icon + '\'' +
                ", messageSection='" + messageSection + '\'' +
                ", time='" + time + '\'' +
                ", chatId='" + chatId + '\'' +
                '}';
    }
}
