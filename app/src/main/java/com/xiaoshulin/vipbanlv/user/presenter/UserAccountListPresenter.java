package com.xiaoshulin.vipbanlv.user.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.TryOutBean;
import com.xiaoshulin.vipbanlv.bean.UserAccountListBean;
import com.xiaoshulin.vipbanlv.user.view.IUserAccountListView;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by jipeng on 2018/10/10.
 */

public class UserAccountListPresenter extends BasePresenter {
    private static final String TAG = "UserAccountListPresente";
    IUserAccountListView iUserAccountListView;

    public UserAccountListPresenter(IUserAccountListView iUserAccountListView) {
        this.iUserAccountListView = iUserAccountListView;
    }


    public void UserAccountList()
    {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign= Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m","user")
                .addParams("a","rentaccountdetail")
                .addParams("uid", stringUId)
                .addParams("machine_type",String.valueOf(1))
                .addParams("sign",sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken",stringUIdToken)
                .addParams("pid",iUserAccountListView.getpid())
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
                        UserAccountListBean bean = gson.fromJson(response, UserAccountListBean.class);
                        iUserAccountListView.getAccountListData(bean);
                    }
                });
    }
}
