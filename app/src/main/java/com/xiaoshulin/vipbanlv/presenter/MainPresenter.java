package com.xiaoshulin.vipbanlv.presenter;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.WebActivity;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.CheckVisionBean;
import com.xiaoshulin.vipbanlv.bean.FreeWhachBean;
import com.xiaoshulin.vipbanlv.bean.LocalDataBean;
import com.xiaoshulin.vipbanlv.bean.MainDataBean;
import com.xiaoshulin.vipbanlv.bean.RegisterBean;
import com.xiaoshulin.vipbanlv.bean.ShareCircleBean;
import com.xiaoshulin.vipbanlv.bean.SupportBean;
import com.xiaoshulin.vipbanlv.bean.danMuBean;
import com.xiaoshulin.vipbanlv.utils.MD5Util;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.MainView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by jp on 2018/4/12.
 */

public class MainPresenter extends BasePresenter{

    private static final String TAG = "MainPresenter";

    MainView mainView;
    public MainPresenter(MainView mainView) {
        super();
        this.mainView=mainView;
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
                .addParams("id",mainView.getid())
                .addParams("url",mainView.geturl())
                .addParams("shareuid",mainView.getshareid())
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
                            mainView.getVideoUrl(freeWhachBean.getUrl());
                        }else if (freeWhachBean.getReturncode().equals("-2")){
                            mainView.showOldMessage("-2");
                        }else{
                            Log.e(TAG, "onResponse: "+response);
                        }

                    }
                });
    }

    public void getDanMuContent()
    {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign=Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m","circle")
                .addParams("a","headnews")
                .addParams("uid", stringUId)
                .addParams("machine_type",String.valueOf(1))
                .addParams("sign",sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken",stringUIdToken)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: "+e.getMessage() );
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "danmu----------------onResponse: "+response);
                        Gson gson=new Gson();
                        danMuBean bean = gson.fromJson(response, danMuBean.class);
                        mainView.getDanmuData(bean);
                    }
                });
    }

    public void getData(){
        /**
         *
         * 生成  token
         * */
        String token = Utils.getNum() + Utils.getTimes();
        Log.e(TAG, "-----token: " + token);

        /**生成  machine
         *
         * */
        String last7 = token.substring(token.length() - 7, token.length());
        Log.e(TAG, "-----last7: " + last7);

        StringBuffer reverseStr = new StringBuffer(last7).reverse();
        Log.e(TAG, "-----reverseStr: " + reverseStr);
        ;
        Integer newString = Integer.parseInt(String.valueOf(reverseStr)) + 6387612;
        Log.e(TAG, "-----newString: " + newString);


        int newNum = Utils.getNUMBER();
        Log.e(TAG, "-----newNum: " + newNum);
        String Newstring = Integer.toString(newString);
        Log.e(TAG, "-----Newstring: " + Newstring);
        String newstr = Newstring.substring(1, Newstring.length());
        Log.e(TAG, "-----newstr: " + newstr);

        String substring = newstr.substring(0, newstr.length() - 1);
        Log.e(TAG, "-----newstr: " + substring);
        String substring1 = newstr.substring(newstr.length() - 1);
        Log.e(TAG, "-----substring1: " + substring1);
        String machine = substring + newNum + substring1;
        Log.e(TAG, "-----machine: " + machine);

        OkHttpUtils
                .get()
                .url(ApiUtils.URL)
                .addParams("m", ApiUtils.M)
                .addParams("a", ApiUtils.A)
                .addParams("machine_type", String.valueOf(1))
                .addParams("token", token)
                .addParams("machine", machine)
//                .addParams("register",register)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e.getMessage() + "       " + call.request());
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        Gson gson = new Gson();
                        RegisterBean registerBean = gson.fromJson(response, RegisterBean.class);
                        mainView.getRegisterdata(registerBean);

                    }
                });

    }

    public void getSupportMoney() {

        int to5 = Utils.get2to5();
        if (to5<2){
            to5=2;
        }

        String appreciateyield=String.valueOf(to5*0.01);

        String num="32413741"+appreciateyield+"E1!?397zx.cASD!@#";
        String stringMD5 = MD5Util.getStringMD5(num);

        String appreciateverify=stringMD5.substring(10,18);

        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "user")
                .addParams("a", "acappreciate")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("appreciateid", "324")
                .addParams("appreciateuid", "1374")
                .addParams("appreciatetype", "1")
                .addParams("appreciateyield", appreciateyield)
                .addParams("appreciateverify", appreciateverify)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "----赏钱结果---onError: " + e.getMessage() + "       " + call.request());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "-----赏钱结果-----onResponse: "+response);
                        Gson gson = new Gson();
                        SupportBean registerBean = gson.fromJson(response, SupportBean.class);
                        mainView.getSupportMoney(registerBean);

                    }
                });

    }

    public void visionNewTag(){


        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        if (TextUtils.isEmpty(stringUId)) {
            stringUId="1";
        }
        String uid = Utils.getUidSign(stringUId);

        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "index")
                .addParams("a", "visionnew")
                .addParams("machine_type", "1")
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", SharePreferenceUtil.getinstance().getStringUIdToken())
                .addParams("uid", stringUId)
                .addParams("sign", uid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(String response, int i) {
                        Gson gson = new Gson();
                        CheckVisionBean checkVisionBean = gson.fromJson(response, CheckVisionBean.class);
                        mainView.getCheckVisionBean(checkVisionBean);
                    }
                });
    }
}
