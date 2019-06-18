package com.xiaoshulin.vipbanlv.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.MainActivity;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.bean.ResolveBean;
import com.xiaoshulin.vipbanlv.bean.ShareCircleBean;
import com.xiaoshulin.vipbanlv.dialog.MainDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.net.URLDecoder;

import okhttp3.Call;

/**
 * Created by jipeng on 2018/12/5.
 */
public class JpushBeanUtils {
    private static final String TAG = "JpushBeanUtils";

    public ResolveBean resolveMessage(Context context, String message) {
        String shareuid, id, url;
        String[] split = message.split("\uD83D\uDC8B");
        if (split.length == 5) {//这是免费内容或通知分享内容的字段分割
            String str = split[1];
            String[] split1 = str.split("\\*");
            shareuid = split1[0];
            id = split1[2];
            url = "";
            String newstring = shareuid + id + url + "397zx.cASD!@#";
            String stringMD5 = MD5Util.getStringMD5(newstring);
            Log.e(TAG, "dostringsomethings: " + stringMD5);
            String checkMD5 = split1[1];
            if (stringMD5.equals(checkMD5)) {
                ResolveBean bean=new ResolveBean(0,split[3],shareuid,url,id);
                return bean;
            }

        } else if (split.length == 7)//分享圈子内容商品的验证
        {
            String first = split[2];
            String[] seconds = first.split("\\*");

            if (seconds.length == 4) {
                String isone = seconds[3];
                if ( isone.equals("1"))
                {
                    /**此时为圈子商品*/
                    String one = seconds[0];
                    String newcheck = one + "http://dwz.cn/86EfALIU" + "http://dwz.cn/lqrDxatM" + seconds[2] + seconds[3] + "397zx.cASD!@#";
                    String stringMD5 = MD5Util.getStringMD5(newcheck);
                    String stirngeight = stringMD5.substring(5, 13);
                    if (stirngeight.equals(seconds[1])) {
                        /**合法*/
                        String s = seconds[0] + seconds[2] + seconds[3] + "397zx.cASD!@#";
                        String s1 = MD5Util.getStringMD5(s);
                        String shareverify = s1.substring(3, 19);
                        //第一个是圈子id,第二个是类型，第三个是分享用户id
                        ResolveBean bean=new ResolveBean(2,seconds[0],seconds[1],seconds[2],"");
                        return bean;
//                        getShareContent(seconds[0], seconds[2], seconds[3], shareverify);
                    }
                }
            }else if (seconds.length==3){
                //第一个是圈子id,第二个是类型，第三个是分享用户id
                ResolveBean bean=new ResolveBean(1,seconds[0],seconds[1],seconds[2],"");
                return bean;
            }
        }

        return new ResolveBean(-1,"","","","");
    }




    private void getShareContent(String shareid, String shareuid, String sharetype, String shareverify) {
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
        String sign = Utils.getUidSign(stringUId);
        OkHttpUtils.get()
                .url(ApiUtils.URL)
                .addParams("m", "circle")
                .addParams("a", "sharecircle")
                .addParams("uid", stringUId)
                .addParams("machine_type", String.valueOf(1))
                .addParams("sign", sign)
                .addParams("version", Utils.getLocalVersionName())
                .addParams("uidtoken", stringUIdToken)
                .addParams("shareid", shareid)
                .addParams("shareuid", shareuid)
                .addParams("sharetype", sharetype)
                .addParams("shareverify", shareverify)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(TAG, "onResponse: " + response);
                        Gson gson = new Gson();
                        ShareCircleBean bean = gson.fromJson(response, ShareCircleBean.class);
                        /**圈子商品跳转*/
                        if (bean.getReturncode().equals("0")) {
                            String url = URLDecoder.decode(bean.getRemark());
                            ARouter.getInstance()
                                    .build("/activity/WebActivity")
                                    .withString("url", url)
                                    .navigation();

                        } else {
                            ToastUtil.showToast("消息过期或修改了消息内容");
                        }
                    }
                });
    }
}
