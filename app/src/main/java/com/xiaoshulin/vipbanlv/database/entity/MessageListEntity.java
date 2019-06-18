package com.xiaoshulin.vipbanlv.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jipeng on 2018/11/27.
 */

@Entity(tableName = "MessageListEntity")
public class MessageListEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String newestMessage;//最新的一条消息

    private String newestTime;//最新的一条消息时间

    private String icon;//用户头像

    private String nickName;//用户昵称

    private String type;//1是我发的消息0对方发的消息888代表客服

    private Integer unreadMessageCount;//未读消息数

    private String messageSection;//消息section用户id

    private String chatListModelId;//消息标记

    private String chatListModelTimeId;//时间戳ID

    private Integer allMessageCount;//所有消息数;
    @Ignore
    public MessageListEntity() {
    }

    public MessageListEntity(String newestMessage, String newestTime, String icon, String nickName, String type, Integer unreadMessageCount, String messageSection, String chatListModelId, String chatListModelTimeId, Integer allMessageCount) {
        this.newestMessage = newestMessage;
        this.newestTime = newestTime;
        this.icon = icon;
        this.nickName = nickName;
        this.type = type;
        this.unreadMessageCount = unreadMessageCount;
        this.messageSection = messageSection;
        this.chatListModelId = chatListModelId;
        this.chatListModelTimeId = chatListModelTimeId;
        this.allMessageCount = allMessageCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewestMessage() {
        return newestMessage;
    }

    public void setNewestMessage(String newestMessage) {
        this.newestMessage = newestMessage;
    }

    public String getNewestTime() {
        return newestTime;
    }

    public void setNewestTime(String newestTime) {
        this.newestTime = newestTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

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

    public Integer getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(Integer unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }

    public String getMessageSection() {
        return messageSection;
    }

    public void setMessageSection(String messageSection) {
        this.messageSection = messageSection;
    }

    public String getChatListModelId() {
        return chatListModelId;
    }

    public void setChatListModelId(String chatListModelId) {
        this.chatListModelId = chatListModelId;
    }

    public String getChatListModelTimeId() {
        return chatListModelTimeId;
    }

    public void setChatListModelTimeId(String chatListModelTimeId) {
        this.chatListModelTimeId = chatListModelTimeId;
    }

    public Integer getAllMessageCount() {
        return allMessageCount;
    }

    public void setAllMessageCount(Integer allMessageCount) {
        this.allMessageCount = allMessageCount;
    }

    @Override
    public String toString() {
        return "MessageListEntity{" +
                "id=" + id +
                ", newestMessage='" + newestMessage + '\'' +
                ", newestTime='" + newestTime + '\'' +
                ", icon='" + icon + '\'' +
                ", nickName='" + nickName + '\'' +
                ", type='" + type + '\'' +
                ", unreadMessageCount=" + unreadMessageCount +
                ", messageSection='" + messageSection + '\'' +
                ", chatListModelId='" + chatListModelId + '\'' +
                ", chatListModelTimeId='" + chatListModelTimeId + '\'' +
                ", allMessageCount=" + allMessageCount +
                '}';
    }
}
