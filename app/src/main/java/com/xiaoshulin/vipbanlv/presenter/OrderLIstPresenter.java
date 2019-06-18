package com.xiaoshulin.vipbanlv.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.OrderListVIew;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.OrderListBean;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;

import okhttp3.Call;

/**
 * Created by jipeng on 2018/5/19.
 */

public class OrderLIstPresenter extends BasePresenter{

    private static final String TAG = "OrderLIstPresenter";

    private OrderListVIew vIew;

    public OrderLIstPresenter(OrderListVIew vIew){
        super();
        this.vIew=vIew;
    }


    public void orderListData(){
        SharePreferenceUtil util=SharePreferenceUtil.getinstance();
        String uid=util.getStringUId();
        String stringUIdToken = util.getStringUIdToken();
//        String uid="1895";
        StringBuffer reverseStr = new StringBuffer(uid).reverse();
        int i = Integer.parseInt(String.valueOf(reverseStr));
        int a= i+19920629;
        String sign1=String.valueOf(a);
        StringBuffer sign2 = new StringBuffer(sign1).reverse();
        String sign=String.valueOf(sign2);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m","user")
                .addParams("a","orderlist")
                .addParams("uid",uid)
                .addParams("machine_type",String.valueOf(1))
                .addParams("sign",sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken",stringUIdToken)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "----------------onError: "+e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: "+response);
                        Gson gson=new Gson();
                        OrderListBean orderListBean = gson.fromJson(response, OrderListBean.class);
                        vIew.getOrderListData(orderListBean);
                    }
                });


    }
}
