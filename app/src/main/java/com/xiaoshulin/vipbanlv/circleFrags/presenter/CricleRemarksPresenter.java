package com.xiaoshulin.vipbanlv.circleFrags.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.CheckVisionBean;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.bean.RemarkInformanton;
import com.xiaoshulin.vipbanlv.circleFrags.view.ICricleRemarksView;
import com.xiaoshulin.vipbanlv.utils.Constants;
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
 * Created by jipeng on 2019/2/22.
 */
public class CricleRemarksPresenter extends BasePresenter {
    private static final String TAG = "CricleRemarksPresenter";
    private ICricleRemarksView iCricleRemarksView;

    public CricleRemarksPresenter(ICricleRemarksView iCricleRemarksView) {
        this.iCricleRemarksView = iCricleRemarksView;
    }

    public void CricleRemarkInformation(String circleid) {
        String readfile = SharePreferenceUtil.getinstance().getStringUId();
        String uid = Utils.getUidSign(readfile);


        String check = circleid + readfile + "1" + "397zx.cASD!@#";
        String check1 = MD5Util.getStringMD5(check);
        String shareverify = check1.substring(3, 19);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "circle")
                .addParams("a", "sharecircle")
                .addParams("machine_type", "1")
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", SharePreferenceUtil.getinstance().getStringUIdToken())
                .addParams("uid", readfile)
                .addParams("sign", uid)
                .addParams("shareid", circleid)
                .addParams("shareuid", readfile)
                .addParams("sharetype", "1")
                .addParams("shareverify", shareverify)
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
                        RemarkInformanton remarkInformanton = gson.fromJson(response, RemarkInformanton.class);
                        if (remarkInformanton.getReturncode().equals("0")) {

                            iCricleRemarksView.getRemarksInformation(remarkInformanton.getRemark());
                        } else {

                            ToastUtil.showToast(remarkInformanton.getMessage());
                        }
                    }
                });
    }


    public void CricleRemarkInformation(String circleid, String readfile) {
        String uid = Utils.getUidSign(readfile);


        String check = circleid + readfile + "1" + "397zx.cASD!@#";
        String check1 = MD5Util.getStringMD5(check);
        String shareverify = check1.substring(3, 19);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "circle")
                .addParams("a", "sharecircle")
                .addParams("machine_type", "1")
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", SharePreferenceUtil.getinstance().getStringUIdToken())
                .addParams("uid", readfile)
                .addParams("sign", uid)
                .addParams("shareid", circleid)
                .addParams("shareuid", readfile)
                .addParams("sharetype", "1")
                .addParams("shareverify", shareverify)
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
                        RemarkInformanton remarkInformanton = gson.fromJson(response, RemarkInformanton.class);
                        if (remarkInformanton.getReturncode().equals("0")) {
                            iCricleRemarksView.getRemarksInformation(remarkInformanton.getRemark());
                        } else {
                            ToastUtil.showToast(remarkInformanton.getMessage());
                        }
                    }
                });
    }


    public void DoCurstomer(Context context, String name, String avatar, String message, String cricleId, String produceid) {
        JPushBean bean = new JPushBean();
        String con;
        if (message.length() > 88) {
            con = message.substring(0, 88);
        } else {
            con = message;
        }

        String mess = "我对您的商品有一些疑惑，想咨询一下。\n" +
                "\n" + "\uD83D\uDC8B" +
                "长按复制这条消息即可进入圈子详情页。\n" +
                "圈子ID：" + "\uD83D\uDC8B" + cricleId + "*8*"+produceid + "\uD83D\uDC8B" + "\n" +
                "请回复问题对应的数字：\n" +
                "1：购买的圈子商品无法查看订单。\n" +
                "2：登录问题。\n" +
                "3：商品使用问题\n" +
                "4：圈子有违法、违纪内容！\n" +
                "5：我有一些好的建议。\n" +
                "\n" + "\uD83D\uDC8B" +
                "在《小树林》APP平台的圈子备注信息：\n" + con + "\n" +
//                "圈子剩余担保期：0天\n" +
                "\uD83D\uDC8B" +
                "发布人ID：" + produceid + "\n" + "\uD83D\uDC8B" +
                "微信客服：xiaoshulinapp";

        bean.setContent(mess);
        JSONObject customfield = new JSONObject();
        try {
            customfield.put("nickName", name);
            customfield.put("type", "0");
            customfield.put("icon", avatar);
            customfield.put("messageSection",SharePreferenceUtil.getinstance().getStringUId());//iOrderInformView.getpushuid()
            customfield.put("time", Utils.getTime1());
            customfield.put("chatId", Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bean.setCustomfield(customfield.toString());
        bean.setPushtype("14");
        /**保存数据*/
        Constants.saveData(context, bean);
        sendInformation(mess, cricleId, avatar,produceid);
    }


    public void sendInformation(String mess, String crlcleuid, String icon,String produceid) {
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
            customfield.put("nickName", "用户ID:" + crlcleuid);
            customfield.put("type", "1");
            customfield.put("icon", icon);
            customfield.put("messageSection", produceid);//iOrderInformView.getpushuid()
            customfield.put("time", Utils.getTime1());
            customfield.put("chatId", Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * verifypushuid的字符串拼接获取
         * */
        int num = Integer.parseInt(produceid) + 539718743;
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
                .addParams("pushuid", produceid)
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


}
