package com.xiaoshulin.vipbanlv.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.UserBean;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.IUserView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by jipeng on 2018/9/16.
 */

public class UserPresenter extends BasePresenter {
    private static final String TAG = "UserPresenter";
    IUserView iUserView;

    public UserPresenter(IUserView iUserView) {
        this.iUserView = iUserView;
    }

    public void getUserInfo()
    {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign=Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m","user")
                .addParams("a","ucenter")
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
                        Log.e(TAG, "onResponse: "+response);
                        Gson gson=new Gson();
                        UserBean userBean = gson.fromJson(response, UserBean.class);
                        iUserView.UserData(userBean);
                    }
                });
    }
}
