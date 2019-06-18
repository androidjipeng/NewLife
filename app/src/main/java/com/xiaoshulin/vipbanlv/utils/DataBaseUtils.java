package com.xiaoshulin.vipbanlv.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.bean.CostomFieldBean;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.database.MessageDatabase;
import com.xiaoshulin.vipbanlv.database.entity.ChatMessageEntity;
import com.xiaoshulin.vipbanlv.database.entity.MessageListEntity;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import io.reactivex.Observable;

/**
 * Created by jipeng on 2018/11/27.
 */
public class DataBaseUtils {
    private static final String TAG = "DataBaseUtils";
    /**插入聊天记录表*/
    public Observable<Boolean> insertChatMessageData(Context context, JPushBean jPushBean) {
        return Observable.create(emitter -> {
            try {
                /**插入到聊天表中*/
                String customfield = jPushBean.getCustomfield();
                Gson gson=new Gson();
                CostomFieldBean costomFieldBean = gson.fromJson(customfield, CostomFieldBean.class);
                //先默认都显示时间吧
                ChatMessageEntity entity = new ChatMessageEntity(jPushBean.getContent(),costomFieldBean.getTime(),costomFieldBean.getIcon(),costomFieldBean.getType(),costomFieldBean.getChatId(),costomFieldBean.getMessageSection(),"1");
                MessageDatabase.getInstance(context).getChatMessageDao().insert(entity);
                Log.e(TAG, "insertChatMessageData:    聊天页面表  插入成功");
                emitter.onNext(true);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "insertChatMessageData:    聊天页面表  插入失败");
                emitter.onNext(false);
            }
            emitter.onComplete();
        });
    }

    /**插入聊天列表*/
    public Observable<Boolean> insertMessageListData(Context context, JPushBean jPushBean) {

        return Observable.create(emitter -> {
            try {
                /**插入聊天列表的表中*/
                String customfield = jPushBean.getCustomfield();
                Gson gson=new Gson();
                CostomFieldBean costomFieldBean = gson.fromJson(customfield, CostomFieldBean.class);

                List<MessageListEntity> messagelist = MessageDatabase.getInstance(context).getMessageListDao().getMessagelist();

                /**筛选是否已经有这个id*/
                boolean isHave=isNewChatData(messagelist,costomFieldBean);
                if (isHave){
                    for (int i = 0; i < messagelist.size(); i++) {
                        if (costomFieldBean.getMessageSection().equals(messagelist.get(i).getMessageSection())) {
                            /*表中有的就去更新数据*/
                            Integer unreadMessageCount = messagelist.get(i).getUnreadMessageCount();
                            unreadMessageCount++;
                            Integer allMessageCount = messagelist.get(i).getAllMessageCount();

                            MessageListEntity listEntity = new MessageListEntity(jPushBean.getContent(),costomFieldBean.getTime(),costomFieldBean.getIcon(),costomFieldBean.getNickName(),costomFieldBean.getType(),unreadMessageCount,costomFieldBean.getMessageSection(),"",costomFieldBean.getChatId(),allMessageCount);
                            listEntity.setId(messagelist.get(i).getId());//需要找到需要更新的那个id，这样才能去更新这一条数据
                            Log.e("jp", listEntity.toString());
                            MessageDatabase.getInstance(context).getMessageListDao().update(listEntity);
                            Log.e("jp", listEntity.toString());

                        }
                    }
                }else {
                        /*表中没有的就去插入数据*/
                        MessageListEntity listEntity = new MessageListEntity(jPushBean.getContent(),costomFieldBean.getTime(),costomFieldBean.getIcon(),costomFieldBean.getNickName(),costomFieldBean.getType(),1,costomFieldBean.getMessageSection(),"",costomFieldBean.getChatId(),1);
                        MessageDatabase.getInstance(context).getMessageListDao().insert(listEntity);

                }

                Log.e(TAG, "insertMessageListData:    聊天列表  插入成功");
                emitter.onNext(true);
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onNext(false);
                Log.e(TAG, "insertMessageListData:    聊天列表  插入失败");
            }
            emitter.onComplete();
        });
    }

    /**看是否是新数据*/
    private boolean isNewChatData(List<MessageListEntity> messagelist, CostomFieldBean costomFieldBean) {
        for (int i = 0; i < messagelist.size(); i++) {
            if (costomFieldBean.getMessageSection().equals(messagelist.get(i).getMessageSection())) {
                      return true;
            }
        }
        return false;
    }


    /**获取聊天消息对话列表*/
    public Observable<List<ChatMessageEntity>> getChatMessageData(Context context, String messageSectionid) {
        return Observable.create(emitter -> {
            try {
                List<ChatMessageEntity> chatMessagelistDesc = MessageDatabase.getInstance(context).getChatMessageDao().getChatMessagelistDesc(messageSectionid);
                emitter.onNext(chatMessagelistDesc);
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onNext(new ArrayList<>());
            }
            emitter.onComplete();
        });
    }


    /**获取聊天消息对话列表*/
    public Observable<List<MessageListEntity>> getMessageListData(Context context) {
        return Observable.create(emitter -> {
            try {
                List<MessageListEntity> messagelistDesc = MessageDatabase.getInstance(context).getMessageListDao().getMessagelist();
                emitter.onNext(messagelistDesc);
            } catch (Exception e) {
                e.printStackTrace();
                emitter.onNext(new ArrayList<>());
            }
            emitter.onComplete();
        });
    }


}
