package com.xiaoshulin.vipbanlv.user.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.VLevelBean;
import com.xiaoshulin.vipbanlv.user.view.IZhuFuBaoView;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * ZhiFuBaoPresenter
 *
 * @author jipeng
 * @date 2018/9/29
 */
public class ZhiFuBaoPresenter extends BasePresenter {
    private static final String TAG = "ZhiFuBaoPresenter";
    IZhuFuBaoView iZhuFuBaoView;

    public ZhiFuBaoPresenter(IZhuFuBaoView iZhuFuBaoView) {
        this.iZhuFuBaoView = iZhuFuBaoView;
    }

    public void ZhiFuBaoCase()
    {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign= Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m","user")
                .addParams("a","dowithdrawals")
                .addParams("uid", stringUId)
                .addParams("machine_type",String.valueOf(1))
                .addParams("sign",sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken",stringUIdToken)
                .addParams("alipay",iZhuFuBaoView.getzhifubaoAccount())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: "+e.getMessage() );
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: "+response);
                        try {
                            JSONObject object=new JSONObject(response);
                            int returncode = object.getInt("returncode");
                            iZhuFuBaoView.zhifuBaoSuccess(returncode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

    }
}
