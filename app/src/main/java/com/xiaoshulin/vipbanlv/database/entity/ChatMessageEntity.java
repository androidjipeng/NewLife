package com.xiaoshulin.vipbanlv.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jipeng on 2018/11/27.
 */
@Entity(tableName = "ChatMessageEntity")
public class ChatMessageEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String text;//文本内容

    private String time;//消息时间

    private String icon;//用户头像

    private String type;//1是我发的消息0对方发的消息888代表客服

    private String chatId;//时间戳ID

    private String messageSection;//消息section用户id

    private String hiddenTime;//是否显示灰色时间cell
    @Ignore
    public ChatMessageEntity() {
    }

    public ChatMessageEntity(String text, String time, String icon, String type, String chatId, String messageSection, String hiddenTime) {

        this.text = text;
        this.time = time;
        this.icon = icon;
        this.type = type;
        this.chatId = chatId;
        this.messageSection = messageSection;
        this.hiddenTime = hiddenTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessageSection() {
        return messageSection;
    }

    public void setMessageSection(String messageSection) {
        this.messageSection = messageSection;
    }

    public String getHiddenTime() {
        return hiddenTime;
    }

    public void setHiddenTime(String hiddenTime) {
        this.hiddenTime = hiddenTime;
    }

    @Override
    public String toString() {
        return "ChatMessageEntity{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", icon='" + icon + '\'' +
                ", type='" + type + '\'' +
                ", chatId='" + chatId + '\'' +
                ", messageSection='" + messageSection + '\'' +
                ", hiddenTime='" + hiddenTime + '\'' +
                '}';
    }
}
