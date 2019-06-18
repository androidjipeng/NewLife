package com.xiaoshulin.vipbanlv;

import android.app.Application;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

/**
 * Created by jp on 2018/4/12.
 */

public class App extends Application {

    public static App app;
    int to41;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        /**
         * 阿里巴巴路由
         * */
        initARouter();
        /**
         * 极光推送
         * */
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        /**
         * 网络请求
         * */
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        /**记录是不是第一次*/
        brotherData();
        String myIcon = SharePreferenceUtil.getinstance().getMyIcon();
        if (myIcon==""){
            to41 = Utils.get0to41();
            SharePreferenceUtil.getinstance().setMyIcon("temporary"+to41);
        }
    }

    private void brotherData() {
        boolean isFrist = SharePreferenceUtil.getinstance().getIsFrist();
        if (!isFrist) {
            to41 = Utils.get0to41();
            SharePreferenceUtil.getinstance().setMyIcon("temporary"+to41);
            Log.e("jp", "accept: -------客服小哥成功   start");
            /**说明是第一次*/
            JPushBean bean = new JPushBean();
            bean.setContent("欢迎来到小树林APP！如果APP给您带来了什么困扰，我可以帮您解答。如果我没有及时回复您的消息，您可以点击下方客服按钮进入我们支付宝客服里反馈。");
            JSONObject customfield = new JSONObject();
            try {
                customfield.put("nickName", "客服小哥");
                customfield.put("type", "888");
                customfield.put("icon", "temporary20");
                customfield.put("messageSection", "1374");//iOrderInformView.getpushuid()
                customfield.put("time", Utils.getTime1());
                customfield.put("chatId", Utils.getTime2());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            bean.setCustomfield(customfield.toString());
            bean.setPushtype("14");
            new DataBaseUtils().insertChatMessageData(app, bean)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                new DataBaseUtils().insertMessageListData(app, bean)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Consumer<Boolean>() {
                                            @Override
                                            public void accept(Boolean aBoolean) throws Exception {
                                                if (aBoolean) {
                                                    /**客服小哥成功之后，执行客服小妹*/
                                                    Log.e("jp", "accept: -------客服小哥成功   end----之后，执行客服小妹 start");
                                                    sisterDada();
//
                                                } else {
                                                    ToastUtil.showToast("插入--聊天列表--失败");
                                                }
                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(Throwable throwable) throws Exception {

                                            }
                                        });
                            } else {
                                ToastUtil.showToast("插入--聊天记录表---的失败");
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e("jp", "插入异常-----------accept: " + throwable);
                        }
                    });

        }
    }


    private void sisterDada() {
        /**说明是第一次*/
        JPushBean bean = new JPushBean();
        bean.setContent("欢迎来到小树林APP！如果APP给您带来了什么困扰，我可以帮您解答。如果我没有及时回复您的消息，您可以点击下方客服按钮进入我们支付宝客服里反馈。");
        JSONObject customfield = new JSONObject();
        try {
            customfield.put("nickName", "客服小妹");
            customfield.put("type", "888");
            customfield.put("icon", "temporary20");
            customfield.put("messageSection", "1377");//iOrderInformView.getpushuid()
            customfield.put("time", Utils.getTime1());
            customfield.put("chatId", Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bean.setCustomfield(customfield.toString());
        bean.setPushtype("14");
        new DataBaseUtils().insertChatMessageData(app, bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            new DataBaseUtils().insertMessageListData(app, bean)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {
                                            if (aBoolean) {
                                                Log.e("jp", "accept: -------执行客服小妹 end");
//                                                /**成功之后，设置为true*/
                                                SharePreferenceUtil.getinstance().setIsFrist(true);
                                            } else {
                                                ToastUtil.showToast("插入--聊天列表--失败");
                                            }
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {

                                        }
                                    });
                        } else {
                            ToastUtil.showToast("插入--聊天记录表---的失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("jp", "插入异常-----------accept: " + throwable);
                    }
                });
    }


    protected void initARouter() {
        if (BuildConfig.DEBUG) {
            // 开启日志
            ARouter.openLog();
            // 使用InstantRun的时候，需要打开该开关，上线之后关闭，否则有安全风险
            ARouter.openDebug();
            // 打印日志的时候打印线程堆栈
            ARouter.printStackTrace();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);
    }
}
