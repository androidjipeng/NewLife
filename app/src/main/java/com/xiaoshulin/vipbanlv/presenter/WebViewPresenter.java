package com.xiaoshulin.vipbanlv.presenter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.xiaoshulin.vipbanlv.activity.OrderListActivity;
import com.xiaoshulin.vipbanlv.view.IwebView;
import com.xiaoshulin.vipbanlv.base.BasePresenter;

/**
 * Created by jipeng on 2018/6/11.
 */

public class WebViewPresenter extends BasePresenter {
    IwebView iwebView;

    public WebViewPresenter(IwebView iwebView) {
        this.iwebView = iwebView;
    }

    public void gotoOrder(Context context)
    {
        Intent intent = new Intent(context, OrderListActivity.class);
        context.startActivity(intent);
    }

    public void gotoAlipay(Context context)
    {
        String message = "#吱口令#长按复制此条消息，打开支付宝即可去小树林APP支付宝客服反馈01MmLE56xb";
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", message);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        //跳转支付宝
        try {
            PackageManager packageManager
                    = context.getApplicationContext().getPackageManager();
            Intent intent = packageManager.
                    getLaunchIntentForPackage("com.eg.android.AlipayGphone");
            context.startActivity(intent);
        } catch (Exception e) {
            String url = "https://ds.alipay.com/?from=mobileweb";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        }
    }

}
