package com.xiaoshulin.vipbanlv.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.NewHomeFragmentBean;
import com.xiaoshulin.vipbanlv.bean.result.NewHomeBottomWelfareBean;
import com.xiaoshulin.vipbanlv.bean.result.NewHomeTopAdvBean;
import com.xiaoshulin.vipbanlv.bean.result.NewHomebottomFriendsBean;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.INewHomeFragmentView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by jipeng on 2019-10-15 10:48.
 */
public class NewHomeFragmentPresenter extends BasePresenter {
    private static final String TAG = "NewHomeFragmentPresente";
    INewHomeFragmentView iNewHomeFragmentView;

    public NewHomeFragmentPresenter(INewHomeFragmentView iNewHomeFragmentView) {
        this.iNewHomeFragmentView = iNewHomeFragmentView;
    }


    public void getFriendsData(){
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "indexlist")
                .addParams("a", "bottom")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("vbottomtime",Utils.getCurrentTime())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "新首页  onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "新首页--------getFriendsData--------onResponse: " + response);
                        Gson gson = new Gson();
                        NewHomebottomFriendsBean bean=gson.fromJson(response, NewHomebottomFriendsBean.class);
                        iNewHomeFragmentView.getNewHomebottomFriendsData(bean);
                    }
                });
    }


    public void getWelfareData(){
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "free")
                .addParams("a", "freevideo")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("pid","1374")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "新首页  onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "新首页---------getWelfareData-------onResponse: " + response);
                        Gson gson = new Gson();
                        NewHomeBottomWelfareBean bean=gson.fromJson(response, NewHomeBottomWelfareBean.class);
                        iNewHomeFragmentView.getNewHomeBottomWelfareData(bean);
                    }
                });
    }


    public void getAdvHomeData(){
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "title")
                .addParams("a", "headline")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "新首页  onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "新首页----------------onResponse: " + response);
                        Gson gson = new Gson();
                        NewHomeTopAdvBean bean=gson.fromJson(response, NewHomeTopAdvBean.class);
                        iNewHomeFragmentView.getNewHomeFragmentAdvData(bean);
                    }
                });
    }


    public void getHomeData() {
        
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "indexlist")
                .addParams("a", "index")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("toplisttime",Utils.getCurrentTime())
                .addParams("newsdatatime",Utils.getCurrentTime())
                .addParams("generalizelisttime",Utils.getCurrentTime())
                .addParams("vlivelisttime",Utils.getCurrentTime())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "新首页  onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "新首页----------------onResponse: " + response);
                        Gson gson = new Gson();
                        NewHomeFragmentBean bean=gson.fromJson(response, NewHomeFragmentBean.class);
                        iNewHomeFragmentView.getNewHomeFragmentData(bean);
                    }
                });

    }


}
