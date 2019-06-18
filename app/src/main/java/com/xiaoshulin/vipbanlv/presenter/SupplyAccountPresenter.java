package com.xiaoshulin.vipbanlv.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.SupplyAccountInforBean;
import com.xiaoshulin.vipbanlv.bean.UserBean;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.ISupplyAccountView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * SupplyAccountPresenter
 *
 * @author jipeng
 * @date 2018/10/22
 */
public class SupplyAccountPresenter extends BasePresenter {
    private static final String TAG = "SupplyAccountPresenter";
    ISupplyAccountView iSupplyAccountView;

    public SupplyAccountPresenter(ISupplyAccountView iSupplyAccountView) {
        this.iSupplyAccountView = iSupplyAccountView;
    }

    public void submit() {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "account")
                .addParams("a", "dorent")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("aid", iSupplyAccountView.getaid())
                .addParams("pid", iSupplyAccountView.getpid())
                .addParams("username", iSupplyAccountView.getusername())
                .addParams("password", iSupplyAccountView.getpassword())
                .addParams("days", iSupplyAccountView.getdays())
                .addParams("wechat", iSupplyAccountView.getwechat())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: " + response);
                        iSupplyAccountView.submitAccountSuccess();
                    }
                });
    }


    public void getAccountInformation()
    {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign= Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m","account")
                .addParams("a","rentaccountdetail")
                .addParams("uid", stringUId)
                .addParams("machine_type",String.valueOf(1))
                .addParams("sign",sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken",stringUIdToken)
                .addParams("aid",iSupplyAccountView.getaid())
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
                        SupplyAccountInforBean bean = gson.fromJson(response, SupplyAccountInforBean.class);
                        iSupplyAccountView.accountInformation(bean);
                    }
                });
    }

}
