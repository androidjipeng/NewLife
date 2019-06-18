package com.xiaoshulin.vipbanlv.view;

import com.xiaoshulin.vipbanlv.base.BaseView;
import com.xiaoshulin.vipbanlv.database.entity.MessageListEntity;

import java.util.List;

/**
 * Created by jipeng on 2018/11/23.
 */
public interface IMessageView {

   void getMessageListData(List<MessageListEntity> messageListEntities);
}
