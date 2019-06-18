package com.xiaoshulin.vipbanlv.utils;

import android.widget.Toast;

import com.xiaoshulin.vipbanlv.App;

/**
 * Created by jipeng on 2018/5/14.
 */

public class ToastUtil {

    public static void showToast(String mess)
    {
       Toast.makeText(App.app.getApplicationContext(),mess,Toast.LENGTH_SHORT).show();
    }

    public static String zhifubao()
    {
        String alipay="吱口令#长按复制此条消息，打开支付宝即可去小树林APP支付宝客服反馈vr4yCC56RC";
        return alipay;
    }
}
