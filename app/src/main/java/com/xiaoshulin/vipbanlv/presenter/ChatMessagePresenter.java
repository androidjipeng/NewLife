package com.xiaoshulin.vipbanlv.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.FreeWhachBean;
import com.xiaoshulin.vipbanlv.bean.ShortCutBean;
import com.xiaoshulin.vipbanlv.database.entity.ChatMessageEntity;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.IChatMessageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;

/**
 * Created by jipeng on 2018/11/23.
 */
public class ChatMessagePresenter extends BasePresenter {
    private static final String TAG = "ChatMessagePresenter";
    IChatMessageView iChatMessageView;

    public ChatMessagePresenter(IChatMessageView iChatMessageView) {
        this.iChatMessageView = iChatMessageView;
    }

    public void ShortCutMessage() {
        List<ShortCutBean> list = new ArrayList<>();
        ShortCutBean bean1 = new ShortCutBean("已发送", "已发送", "不客气", "不客气");
        list.add(bean1);
        ShortCutBean bean2 = new ShortCutBean("可以吗", "可以吗", "再来一次", "再来一次");
        list.add(bean2);
        ShortCutBean bean3 = new ShortCutBean("要验证码", "要验证码", "超时了", "超时了");
        list.add(bean3);
        ShortCutBean bean4 = new ShortCutBean("稍等", "稍等", "成功了吗", "成功了吗");
        list.add(bean4);
        ShortCutBean bean5 = new ShortCutBean("OK了", "OK了", "微信客服", "长按复制客服微信号：《xiaoshulinapp》去小树林APP客服微信反馈");
        list.add(bean5);
        ShortCutBean bean6 = new ShortCutBean("谢谢了", "谢谢了", "阿里客服", "#吱口令#长按复制此条消息，打开支付宝即可去小树林APP支付宝客服反馈01MmLE56xb");
        list.add(bean6);
        iChatMessageView.getShortCutMess(list);
    }

    public void chatMessagedata(Context context, String id) {
        DataBaseUtils dataBaseUtils = new DataBaseUtils();
        dataBaseUtils.getChatMessageData(context, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ChatMessageEntity>>() {
                    @Override
                    public void accept(List<ChatMessageEntity> chatMessageEntities) throws Exception {
                        iChatMessageView.getChatMessagedata(chatMessageEntities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("jp", "accept: " + throwable);
                    }
                });
    }

    public void sendInformation(String mess) {
        /**
         * 编辑content的字符串编辑
         * */
        String content = mess;

        /**
         * 获得本地用户的uid
         * */
        SharePreferenceUtil util = SharePreferenceUtil.getinstance();
        String stringUId = util.getStringUId();
        String stringUIdToken = util.getStringUIdToken();
        String myicon=util.getMyIcon();
//        String stringUId="1895";
        /**
         * 获得sign字符串
         * */
        StringBuffer reverseStr = new StringBuffer(stringUId).reverse();
        int i = Integer.parseInt(String.valueOf(reverseStr));
        int a = i + 19920629;
        String sign1 = String.valueOf(a);
        StringBuffer sign2 = new StringBuffer(sign1).reverse();
        String sign = String.valueOf(sign2);

        /**
         * 拼接customfield的json对象
         * */
        JSONObject customfield = new JSONObject();
        try {
            customfield.put("nickName", "用户ID:" + stringUId);
            customfield.put("type", "1");
            customfield.put("icon", myicon);
            customfield.put("messageSection", stringUId);//iOrderInformView.getpushuid()
            customfield.put("time", Utils.getTime1());
            customfield.put("chatId", Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * verifypushuid的字符串拼接获取
         * */
        int num = Integer.parseInt(iChatMessageView.getmessageSectionid()) + 539718743;
        String A = num + "";
        StringBuffer stringBuffer = new StringBuffer(A).reverse();/*倒叙*/
        String B = stringBuffer.toString();
        String verifypushuid = Utils.getNUMBER() + B + Utils.getNUMBER();

        OkHttpUtils
                .get()
                .url(ApiUtils.URL)
                .addParams("m", "chat")
                .addParams("a", "chatjpush")
                .addParams("uid", stringUId)
                .addParams("machine_type", "1")
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("pushuid", iChatMessageView.getmessageSectionid())
                .addParams("verifypushuid", verifypushuid)
                .addParams("alert", "用户ID:" + stringUId + ":"+content+ "Android")  //您提供的试用账号使用中遇到了问题
                .addParams("content", content)
                .addParams("pushtype", "14")
                .addParams("customfield", customfield.toString())
                .addParams("type", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "..........onResponse:     success:" + response);
                        iChatMessageView.sendSuccess();
                    }
                });

    }


    public void getVideoUrl()
    {
        SharePreferenceUtil util = SharePreferenceUtil.getinstance();
        String stringUId = util.getStringUId();
        String stringUIdToken = util.getStringUIdToken();
        StringBuffer reverseStr = new StringBuffer(stringUId).reverse();
        int i = Integer.parseInt(String.valueOf(reverseStr));
        int a= i+19920629;
        String sign1=String.valueOf(a);
        StringBuffer sign2 = new StringBuffer(sign1).reverse();
        String sign=String.valueOf(sign2);
        OkHttpUtils
                .get()
                .url(ApiUtils.URL)
                .addParams("m","tryseeshare")
                .addParams("a","tryseeshareurl")
                .addParams("uid",stringUId)
                .addParams("machine_type","1")
                .addParams("sign",sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken",stringUIdToken)
                .addParams("id",iChatMessageView.getResolveBean().getId())
                .addParams("url",iChatMessageView.getResolveBean().getUrl())
                .addParams("shareuid",iChatMessageView.getResolveBean().getShareuid())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: "+ e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: "+response);
                        Gson gson=new Gson();
                        FreeWhachBean freeWhachBean = gson.fromJson(response, FreeWhachBean.class);
                        if (freeWhachBean.getReturncode().equals("0"))
                        {
                            iChatMessageView.getVideoUrl(freeWhachBean.getUrl());
                        }else if (freeWhachBean.getReturncode().equals("-2")){
                            iChatMessageView.showOldMessage("-2");
                        }else{
                            Log.e(TAG, "onResponse: "+response);
                        }

                    }
                });
    }
}
