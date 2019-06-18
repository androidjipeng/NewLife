package com.xiaoshulin.vipbanlv.presenter;

import android.content.Context;
import android.util.Log;

import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.database.entity.MessageListEntity;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.view.IMessageView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jipeng on 2018/11/23.
 */
public class MessagePresenter extends BasePresenter {
    private static final String TAG = "MessagePresenter";
    private IMessageView iMessageView;

    public MessagePresenter(IMessageView iMessageView) {
        this.iMessageView = iMessageView;
    }

    public void MessageListData(Context context)
    {
        new DataBaseUtils().getMessageListData(context)
                .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MessageListEntity>>() {
                    @Override
                    public void accept(List<MessageListEntity> messageListEntities) throws Exception {
                          iMessageView.getMessageListData(messageListEntities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "accept: "+throwable);
                    }
                });
    }
}
