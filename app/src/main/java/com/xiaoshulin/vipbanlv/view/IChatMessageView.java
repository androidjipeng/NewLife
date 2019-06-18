package com.xiaoshulin.vipbanlv.view;

import com.xiaoshulin.vipbanlv.bean.ResolveBean;
import com.xiaoshulin.vipbanlv.bean.ShortCutBean;
import com.xiaoshulin.vipbanlv.database.entity.ChatMessageEntity;

import java.util.List;

/**
 * Created by jipeng on 2018/11/23.
 */
public interface IChatMessageView {
    void getChatMessagedata(List<ChatMessageEntity> list);
     String getmessageSectionid();
     void sendSuccess();

     void getShortCutMess(List<ShortCutBean> shortCutBeanList);

     ResolveBean getResolveBean();
    void getVideoUrl(String url);
    void showOldMessage(String mess);
}
