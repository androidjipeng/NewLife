package com.xiaoshulin.vipbanlv.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.bean.CostomFieldBean;
import com.xiaoshulin.vipbanlv.bean.JPushBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Constants {
    private static final String TAG = "Constants";
    public static final String APP_ID = "wx8c124f3cab93b595";

    public static void saveData(Context context,JPushBean jPushBean) {
        new DataBaseUtils().insertChatMessageData(context, jPushBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            new DataBaseUtils().insertMessageListData(context, jPushBean)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {
                                            if (aBoolean) {
                                                String customfield = jPushBean.getCustomfield();
                                                Gson gson = new Gson();
                                                CostomFieldBean costomFieldBean = gson.fromJson(customfield, CostomFieldBean.class);
                                                ARouter.getInstance()
                                                        .build("/activity/ChatMessageActivity")
                                                        .withString("titleName", costomFieldBean.getNickName())
                                                        .withString("messageSectionid", costomFieldBean.getMessageSection())
                                                        .navigation();
                                            } else {
                                                ToastUtil.showToast("插入--聊天列表--失败");
                                            }
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            Log.e(TAG, "accept: " + throwable);
                                        }
                                    });

                        } else {
                            ToastUtil.showToast("插入--聊天记录表---的失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "插入异常-----------accept: " + throwable);
                    }
                });
    }
}
