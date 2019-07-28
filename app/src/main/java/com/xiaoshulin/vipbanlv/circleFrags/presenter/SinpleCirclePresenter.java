package com.xiaoshulin.vipbanlv.circleFrags.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.CheckVisionBean;
import com.xiaoshulin.vipbanlv.bean.CircleAdvertisementBean;
import com.xiaoshulin.vipbanlv.bean.CricleBean;
import com.xiaoshulin.vipbanlv.bean.CricleBuyPro;
import com.xiaoshulin.vipbanlv.bean.CricleBuyToInterial;
import com.xiaoshulin.vipbanlv.bean.SupportBean;
import com.xiaoshulin.vipbanlv.bean.singleCircleBean;
import com.xiaoshulin.vipbanlv.circleFrags.view.ISinpleCircleView;
import com.xiaoshulin.vipbanlv.utils.MD5Util;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by jipeng on 2019/3/29.
 */
public class SinpleCirclePresenter extends BasePresenter {

    private static final String TAG = "SinpleCirclePresenter";

    private ISinpleCircleView iSinpleCircleView;

    public SinpleCirclePresenter(ISinpleCircleView iSinpleCircleView) {
        this.iSinpleCircleView = iSinpleCircleView;
    }

    public void getSinpleCircleData(String cricleid) {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "circle")
                .addParams("a", "getcircle")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("circleid", cricleid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: ---------------------------------?" + response);
                        Gson gson = new Gson();
                        singleCircleBean cricleBean = gson.fromJson(response, singleCircleBean.class);
                        if (cricleBean.getReturncode().equals("0")){

                            iSinpleCircleView.CircleListData(cricleBean);
                        }else {
                            ToastUtil.showToast("returncode:"+cricleBean.getReturncode());
                        }
                    }
                });
    }

    public void getCircleAdvertisement() {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "circle")
                .addParams("a", "typead")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("circlelisttype", iSinpleCircleView.circlelisttype())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "Advertisement---------------onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "Advertisement--------------onResponse: " + response);
                        Gson gson = new Gson();
                        CircleAdvertisementBean bean = gson.fromJson(response, CircleAdvertisementBean.class);
                        iSinpleCircleView.CircleAdv(bean);
                    }
                });
    }

    public void IsCollect(String circleid, String iscollect) {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "circle")
                .addParams("a", "docollect")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("circleid", circleid)
                .addParams("iscollect", iscollect)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "IsCollect---------------onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "IsCollect--------------onResponse: " + response);
                        try {
                            JSONObject object = new JSONObject(response);
                            int returncode = object.getInt("returncode");
                            String mess = object.getString("message");

                            iSinpleCircleView.collectSuccess(returncode, mess);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 购买商品
     */
    public void BuyPro(String commodityid, String paytype) {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "circle")
                .addParams("a", "createorder")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("commodityid", commodityid)
                .addParams("commoditytype", "1")
                .addParams("paytype", paytype)
                .addParams("extendjson", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "购买商品---------------onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "购买商品--------------onResponse: " + response);
                        Gson gson = new Gson();

                        if (paytype.equals("1")) {
                            Log.e(TAG, "onResponse:   -------支付宝支付");
                            /*支付宝支付*/
                            CricleBuyPro cricleBuyPro = gson.fromJson(response, CricleBuyPro.class);
                            iSinpleCircleView.payToAlipay(cricleBuyPro);
                        } else {
                            /*积分支付*/
                            Log.e(TAG, "onResponse:  ------积分支付" + response);
                            CricleBuyToInterial cricleBuyToInterial = gson.fromJson(response, CricleBuyToInterial.class);
                            iSinpleCircleView.payToIntegral(cricleBuyToInterial);
                        }

                    }
                });
    }

    /**
     * 删除商品
     */
    public void delectCircle(String circleid) {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "circle")
                .addParams("a", "createorder")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("circleid", circleid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "IsCollect---------------onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "IsCollect--------------onResponse: " + response);
                        try {
                            JSONObject object = new JSONObject(response);
                            int returncode = object.getInt("returncode");
                            iSinpleCircleView.DelectCircleSuccess(returncode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    public void sendInformation(String mess, String crlcleid, String icon) {
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
            customfield.put("nickName", "用户ID:" + crlcleid);
            customfield.put("type", "1");
            customfield.put("icon", icon);
            customfield.put("messageSection", stringUId);//iOrderInformView.getpushuid()
            customfield.put("time", Utils.getTime1());
            customfield.put("chatId", Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * verifypushuid的字符串拼接获取
         * */
        int num = Integer.parseInt(crlcleid) + 539718743;
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
                .addParams("pushuid", crlcleid)
                .addParams("verifypushuid", verifypushuid)
                .addParams("alert", "用户ID:" + stringUId + ":" + content + "Android")  //您提供的试用账号使用中遇到了问题
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
//                        iChatMessageView.sendSuccess();
                    }
                });
    }

    public void V_money() {
        String readfile = SharePreferenceUtil.getinstance().getStringUId();
        String uid = Utils.getUidSign(readfile);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "index")
                .addParams("a", "visionnew")
                .addParams("machine_type", "1")
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", SharePreferenceUtil.getinstance().getStringUIdToken())
                .addParams("uid", readfile)
                .addParams("sign", uid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError:    " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse:     " + response);
                        Gson gson = new Gson();
                        CheckVisionBean checkVisionBean = gson.fromJson(response, CheckVisionBean.class);
                        iSinpleCircleView.getV_money(checkVisionBean);
                    }
                });
    }


    public void PaySuccessOk(String orderid,String paytype) {
        String readfile = SharePreferenceUtil.getinstance().getStringUId();
        String uid = Utils.getUidSign(readfile);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "circle")
                .addParams("a", "payok")
                .addParams("machine_type", "1")
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", SharePreferenceUtil.getinstance().getStringUIdToken())
                .addParams("uid", readfile)
                .addParams("sign", uid)
                .addParams("orderid",orderid)
                .addParams("paytype",paytype)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError:    " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse:     " + response);
                        try {
                            JSONObject object = new JSONObject(response);
                            String newid = object.getString("newid");
                            iSinpleCircleView.payisOk(newid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**欣赏*/
    public void getSupportMoney(String circleid,String produceid) {

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
                .addParams("appreciateid", circleid)
                .addParams("appreciateuid", produceid)
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
                        iSinpleCircleView.enjoy(registerBean);

                    }
                });

    }
}
