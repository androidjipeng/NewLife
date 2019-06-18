package com.xiaoshulin.vipbanlv.presenter;

import android.util.Log;

import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.IOrderInformView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by jipeng on 2018/5/29.
 */

public class OrderInformPresenter extends BasePresenter {

    private static final String TAG = "OrderInformPresenter";

    IOrderInformView iOrderInformView;

    public OrderInformPresenter(IOrderInformView iOrderInformView) {
        super();
        this.iOrderInformView = iOrderInformView;
    }



    public void sendInformation1()
    {
        /**
         * 编辑content的字符串编辑
         * */
        String content="试用账号使用中遇到了问题\n" +
                "\n" +
                "查看或回复问题对应的数字：\n" +
                "1：登录时提示账号密码错误。\n" +
                "2：登录时需要验证码。\n" +
                "3：使用中提示账号异常无法使用VIP服务。\n" +
                "4：提示账号不是VIP。\n" +
                "\n" +
                "页面上账号的相关信息：\n"+"在《小树林》APP平台的账号信息:\n"+iOrderInformView.getcontent();

        /**
         * 获得本地用户的uid
         * */
        SharePreferenceUtil util = SharePreferenceUtil.getinstance();
        String stringUId = util.getStringUId();
        String stringUIdToken = util.getStringUIdToken();
//        String stringUId="1895";
        /**
         * 获得sign字符串
         * */
        StringBuffer reverseStr = new StringBuffer(stringUId).reverse();
        int i = Integer.parseInt(String.valueOf(reverseStr));
        int a= i+19920629;
        String sign1=String.valueOf(a);
        StringBuffer sign2 = new StringBuffer(sign1).reverse();
        String sign=String.valueOf(sign2);

        /**
         * 拼接customfield的json对象
         * */
        JSONObject customfield=new JSONObject();
        try {
            customfield.put("nickName","用户ID:"+stringUId);
            customfield.put("type","1");
            customfield.put("icon","temporary20");
            customfield.put("messageSection",stringUId);
            customfield.put("time",Utils.getTime1());
            customfield.put("chatId",Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * verifypushuid的字符串拼接获取
         * */
        int num = Integer.parseInt(iOrderInformView.getpushuid1()) + 539718743;
        String A=num+"";
        StringBuffer stringBuffer = new StringBuffer(A).reverse();/*倒叙*/
        String B = stringBuffer.toString();
        String verifypushuid=Utils.getNUMBER()+B+Utils.getNUMBER();

        OkHttpUtils
                .get()
                .url(ApiUtils.URL)
                .addParams("m","chat")
                .addParams("a","chatjpush")
                .addParams("uid",stringUId)
                .addParams("machine_type","1")
                .addParams("sign",sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken",stringUIdToken)
                .addParams("pushuid",iOrderInformView.getpushuid1())
                .addParams("verifypushuid",verifypushuid)
                .addParams("alert","用户ID:"+stringUId+":您提供的试用账号使用中遇到了问题"+"Android")
                .addParams("content",content)
                .addParams("pushtype","14")
                .addParams("customfield",customfield.toString())
                .addParams("type","0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: "+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: "+response);
                        saveSendMessage1(content);
                    }
                });

    }

    public void sendInformation()
    {
        /**
         * 编辑content的字符串编辑
         * */
        String content="试用账号使用中遇到了问题\n" +
                "\n" +
                "查看或回复问题对应的数字：\n" +
                "1：登录时提示账号密码错误。\n" +
                "2：登录时需要验证码。\n" +
                "3：使用中提示账号异常无法使用VIP服务。\n" +
                "4：提示账号不是VIP。\n" +
                "\n" +
                "页面上账号的相关信息：\n"+"在《小树林》APP平台的账号信息:\n"+iOrderInformView.getcontent();

        /**
         * 获得本地用户的uid
         * */
        SharePreferenceUtil util = SharePreferenceUtil.getinstance();
        String stringUId = util.getStringUId();
        String stringUIdToken = util.getStringUIdToken();
//        String stringUId="1895";
        /**
         * 获得sign字符串
         * */
        StringBuffer reverseStr = new StringBuffer(stringUId).reverse();
        int i = Integer.parseInt(String.valueOf(reverseStr));
        int a= i+19920629;
        String sign1=String.valueOf(a);
        StringBuffer sign2 = new StringBuffer(sign1).reverse();
        String sign=String.valueOf(sign2);

        /**
         * 拼接customfield的json对象
         * */
        JSONObject customfield=new JSONObject();
        try {
            customfield.put("nickName","用户ID:"+stringUId);
            customfield.put("type","1");
            customfield.put("icon","temporary20");
            customfield.put("messageSection",stringUId);
            customfield.put("time",Utils.getTime1());
            customfield.put("chatId",Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * verifypushuid的字符串拼接获取
         * */
        int num = Integer.parseInt(iOrderInformView.getpushuid()) + 539718743;
        String A=num+"";
        StringBuffer stringBuffer = new StringBuffer(A).reverse();/*倒叙*/
        String B = stringBuffer.toString();
        String verifypushuid=Utils.getNUMBER()+B+Utils.getNUMBER();

        OkHttpUtils
                .get()
                .url(ApiUtils.URL)
                .addParams("m","chat")
                .addParams("a","chatjpush")
                .addParams("uid",stringUId)
                .addParams("machine_type","1")
                .addParams("sign",sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken",stringUIdToken)
                .addParams("pushuid",iOrderInformView.getpushuid())
                .addParams("verifypushuid",verifypushuid)
                .addParams("alert","用户ID:"+stringUId+":您提供的试用账号使用中遇到了问题"+"Android")
                .addParams("content",content)
                .addParams("pushtype","14")
                .addParams("customfield",customfield.toString())
                .addParams("type","0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: "+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: "+response);
                        saveSendMessage(content);
                    }
                });

    }


    public void sendServiceInformation()
    {
        /**
         * 编辑content的字符串编辑
         * */
        String content="我在app使用中遇到了问题，请帮我解答一下。";

        /**
         * 获得本地用户的uid
         * */
        SharePreferenceUtil util = SharePreferenceUtil.getinstance();
        String stringUId = util.getStringUId();
        String stringUIdToken = util.getStringUIdToken();
//        String stringUId="1895";
        /**
         * 获得sign字符串
         * */
        StringBuffer reverseStr = new StringBuffer(stringUId).reverse();
        int i = Integer.parseInt(String.valueOf(reverseStr));
        int a= i+19920629;
        String sign1=String.valueOf(a);
        StringBuffer sign2 = new StringBuffer(sign1).reverse();
        String sign=String.valueOf(sign2);

        /**
         * 拼接customfield的json对象
         * */
        JSONObject customfield=new JSONObject();
        try {
            customfield.put("nickName","用户ID:"+stringUId);
            customfield.put("type","1");
            customfield.put("icon","temporary20");
            customfield.put("messageSection",stringUId);
            customfield.put("time",Utils.getTime1());
            customfield.put("chatId",Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * verifypushuid的字符串拼接获取
         * */
        int num = Integer.parseInt(iOrderInformView.getpushuid()) + 539718743;
        String A=num+"";
        StringBuffer stringBuffer = new StringBuffer(A).reverse();/*倒叙*/
        String B = stringBuffer.toString();
        String verifypushuid=Utils.getNUMBER()+B+Utils.getNUMBER();

        OkHttpUtils
                .get()
                .url(ApiUtils.URL)
                .addParams("m","chat")
                .addParams("a","chatjpush")
                .addParams("uid",stringUId)
                .addParams("machine_type","1")
                .addParams("sign",sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken",stringUIdToken)
                .addParams("pushuid",iOrderInformView.getpushuid())
                .addParams("verifypushuid",verifypushuid)
                .addParams("alert","用户ID:"+stringUId+":您提供的试用账号使用中遇到了问题"+"Android")
                .addParams("content",content)
                .addParams("pushtype","14")
                .addParams("customfield",customfield.toString())
                .addParams("type","0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: "+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: "+response);
                        saveSendMessage(content);
                    }
                });

    }

    private void saveSendMessage(String content) {
        JPushBean bean = new JPushBean();
        bean.setContent(content);
        JSONObject customfield = new JSONObject();
        try {
            customfield.put("nickName", "用户ID:" + iOrderInformView.getpushuid());
            customfield.put("type", "0");
            customfield.put("icon", "temporary20");
            customfield.put("messageSection", iOrderInformView.getpushuid());
            customfield.put("time", Utils.getTime1());
            customfield.put("chatId", Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bean.setCustomfield(customfield.toString());
        bean.setPushtype("14");
        iOrderInformView.show(bean);
    }

    private void saveSendMessage1(String content) {
        JPushBean bean = new JPushBean();
        bean.setContent(content);
        JSONObject customfield = new JSONObject();
        try {
            customfield.put("nickName", "用户ID:" + iOrderInformView.getpushuid1());
            customfield.put("type", "0");
            customfield.put("icon", "temporary20");
            customfield.put("messageSection", iOrderInformView.getpushuid1());
            customfield.put("time", Utils.getTime1());
            customfield.put("chatId", Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bean.setCustomfield(customfield.toString());
        bean.setPushtype("14");
        iOrderInformView.show(bean);
    }
}
