package com.xiaoshulin.vipbanlv;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.view.menu.MenuWrapperFactory;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.activity.OrderListActivity;
import com.xiaoshulin.vipbanlv.bean.AlipayBean;
import com.xiaoshulin.vipbanlv.bean.CostomFieldBean;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.bean.ParseBean;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.jpush.LocalBroadcastManager;
import com.xiaoshulin.vipbanlv.popup.JpushPopupwindow;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.MD5Util;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.utils.WVJBWebViewClient;
import com.xiaoshulin.vipbanlv.view.IwebView;

import org.json.JSONException;
import org.json.JSONObject;

import com.xiaoshulin.vipbanlv.alipay.PayResult;
import com.xiaoshulin.vipbanlv.alipay.SignUtils;
import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.presenter.WebViewPresenter;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Route(path = "/activity/WebActivity")
public class WebActivity extends BaseMVPActivity implements IwebView, View.OnClickListener {
    private static final String TAG = "WebActivity";

    @Autowired
    public String url;

    Activity activity;
    WebView webview;

    int postion;
    WebViewPresenter presenter;

    AlipayBean alipayBean;

    // 商户PID
    public static final String PARTNER = "2088621796126903";
    // 商户收款账号
    public static final String SELLER = "2867390698@qq.com";

    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALgwhRO7anREaLNFwt0V6Jc//dUJ4aUMso1g9qCvNEpBhQpGfw6JDcjqSDPwjD/XUSdxGeo0EY9C/gzHd+B7iI5a0iJlTVshKJmcUrssB1bL2pSwNGOpDCShpoTyk7SleZyXltWkEnJpVxoqWk3Xfxl4xQ594CKkckaWD+ExIGRlAgMBAAECgYBAbVhfSpM2ECe/bYt34g31ugCD4Gz5WrOqGFysYByeTMTIj1gqMW6M9MZExMOKITrV3Q3/ii8KOZi7x5n6/7sH15QQW2vFgd7BzD4VGuEUYYknXfAEJ7ljcVQtbIE4lTf/Ax+G9PEgfR096CoU/gmXT/+FuojVz+uXskycpNYGEQJBAPInhFgBTeDzzXc/MunCP4tRzRkyBdr2T+6SU3qLjvW53JH0Vk/I5yNkM4twNC4+Ny3fZsjl29lt4MVco3YRG5MCQQDCuI4Q6fKQm6y1cu5RTI8SGw/JIpglZYe/opzviZY53DOuRyAWxtNYMwMmFYGEGrikmywKC0qru6K5HH5wvqsnAkBitrFAnud8eGad4emDWWhGBwaw6q9wX1CeVJArOJECos3DwfMdeyyeRM6uXQMohrw/uv5Pj0RcEmq6idUo4Dh5AkEAkmjsQSF3Bm5XHROux6hWBxob83M0jSHbQSdG8wEz2IuOvGK0aQvUi4PHwVH4UVk6LmKSaXb2DUyHXMJk++cRjwJAHeBYuCdHkowGqpejZMJmluXaaxVVMm2HUntdhWsdDHxva3jplYB0m74VDkFDBJxAx/L40AF7LjTGrFRBko1qKA==";

    // 支付宝公钥
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;

    private WVJBWebViewClient webViewClient;

    private TextView tv_order, tv_complaint;
    ProgressBar roundProgressBar;

    PremissionsDialog dialog;
    /**
     * start监听光广播
     */
    private JpushPopupwindow window1;

    CheckUpLoadDialog premissdialog;
    /**
     * 视频全屏参数
     */

    protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    private View customView;

    private FrameLayout fullscreenContainer;

    private WebChromeClient.CustomViewCallback customViewCallback;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.e("jp", "--------------------------onConfigurationChanged: " + newConfig.toString());
    }


    @Override

    protected void onStop() {

        super.onStop();

        webview.reload();

    }


    @Override

    protected void onResume() {

        super.onResume();

        webview.onResume();

        webview.resumeTimers();

    }

    @Override
    protected void onDestroy() {
        webview.stopLoading();

        webview.setWebChromeClient(null);

        webview.setWebViewClient(null);

        webview = null;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

                    Bundle bundle = intent.getExtras();
                    String ALERT = bundle.getString(JPushInterface.EXTRA_ALERT);
                    String EXTRA = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    Gson gson = new Gson();
                    JPushBean jPushBean = gson.fromJson(EXTRA, JPushBean.class);
                    setCostomMsg(ALERT, jPushBean.getContent());
                    /**
                     * 作弹框区分
                     * */
                    if (jPushBean.getPushtype().equals("2")) {
                        premissdialog = new CheckUpLoadDialog(context, jPushBean.getALERT(), jPushBean.getContent(), "取消", "去反馈", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.tv_cancel:
                                        premissdialog.dismiss();
                                        break;
                                    case R.id.tv_submit:
                                        premissdialog.dismiss();
                                        /*来收入通知*/
                                        ARouter.getInstance()
                                                .build("/user/UserInformation")
                                                .withInt("tag", 2)
                                                .navigation();
                                        break;
                                }

                            }
                        });

                        premissdialog.show();

                    } else if (jPushBean.getPushtype().equals("3")) {
                        premissdialog = new CheckUpLoadDialog(context, "通知", "您的账号没有通过审核！", "取消", "去反馈", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.tv_cancel:
                                        premissdialog.dismiss();
                                        break;
                                    case R.id.tv_submit:
                                        premissdialog.dismiss();
                                        /**小树林支付宝客服*/
                                        jumpAlipay(ToastUtil.zhifubao());
                                        break;
                                }

                            }
                        });

                        premissdialog.show();
                    } else if (jPushBean.getPushtype().equals("14")) {
//                        ToastUtil.showToast("Pushtype为14，去要验证码页面，待开发...");
                        Log.e(TAG, "========onReceive: ");
//                        saveData(jPushBean);
                    }
                }
            } catch (Exception e) {
            }
        }
    }


    @SuppressLint("NewApi")
    private void setCostomMsg(String ALERT, String content) {
        window1 = new JpushPopupwindow(WebActivity.this, ALERT, content);
        window1.showAtLocation(findViewById(R.id.re),
                Gravity.CENTER | Gravity.TOP,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, activity.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, activity.getResources().getDisplayMetrics()));
        window1.showAsDropDown(findViewById(R.id.re),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, activity.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, activity.getResources().getDisplayMetrics()),
                Gravity.CENTER | Gravity.TOP);
        window1.update();

    }

    /**
     * end
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate:        ---------》");
        activity = this;
        registerMessageReceiver();


        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        /**为视频播放添加*/
        webSettings.setPluginState(WebSettings.PluginState.ON);

        webSettings.setDomStorageEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        //打开js支持 /** * 打开js接口給H5调用，参数1为本地类名，参数2为别名；h5用window.别名.类名里的方法名才能调用方法里面的内容，例如：window.android.back(); * */
        webview.addJavascriptInterface(new JsInteration(), "vipbanlvFunc");

        /**
         * 针对H5页面，
         启动webview的html5的本地存储功能。
         */
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webview.getSettings().setAppCachePath(appCachePath);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setAppCacheEnabled(true);

        webViewClient = new MyWebViewClient(webview);
        webViewClient.enableLogging();
        webview.setWebViewClient(webViewClient);
        webview.setWebChromeClient(
                new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        if (newProgress == 100) {
                            roundProgressBar.setVisibility(View.GONE);
                        } else {
                            if (View.GONE == roundProgressBar.getVisibility()) {
                                roundProgressBar.setVisibility(View.VISIBLE);
                            }
                            roundProgressBar.setProgress(newProgress);
                        }
                        super.onProgressChanged(view, newProgress);
                    }

                    /*** 视频播放相关的方法 **/


                    @Override

                    public View getVideoLoadingProgressView() {

                        FrameLayout frameLayout = new FrameLayout(WebActivity.this);

                        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

                        return frameLayout;

                    }

                    @Override

                    public void onShowCustomView(View view, CustomViewCallback callback) {

                        showCustomView(view, callback);

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//播放时横屏幕，如果需要改变横竖屏，只需该参数就行了
                    }

                    @Override

                    public void onHideCustomView() {

                        hideCustomView();

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//不播放时竖屏

                    }
                }

        );

        /***/

        webview.loadUrl(url);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.evaluateJavascript("vipbanlvPayCall", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {
                    Log.e("jp", "=====================onReceiveValue: " + s);
                }
            });
        }


    }


    /**
     * 视频播放全屏
     **/

    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {

        // if a view already exists then immediately terminate the new one

        if (customView != null) {

            callback.onCustomViewHidden();

            return;

        }


        WebActivity.this.getWindow().getDecorView();


        FrameLayout decor = (FrameLayout) getWindow().getDecorView();

        fullscreenContainer = new FullscreenHolder(this);

        fullscreenContainer.addView(view, COVER_SCREEN_PARAMS);

        decor.addView(fullscreenContainer, COVER_SCREEN_PARAMS);

        customView = view;

        setStatusBarVisibility(false);

        customViewCallback = callback;

    }


    /**
     * 隐藏视频全屏
     */

    private void hideCustomView() {

        if (customView == null) {

            return;

        }


        setStatusBarVisibility(true);

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();

        decor.removeView(fullscreenContainer);

        fullscreenContainer = null;

        customView = null;

        customViewCallback.onCustomViewHidden();

        webview.setVisibility(View.VISIBLE);

    }


    /**
     * 全屏容器界面
     */

    static class FullscreenHolder extends FrameLayout {


        public FullscreenHolder(Context ctx) {

            super(ctx);

            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));

        }


        @Override

        public boolean onTouchEvent(MotionEvent evt) {

            return true;

        }

    }


    private void setStatusBarVisibility(boolean visible) {

        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;

        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


    @Override

    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (keyCode) {

            case KeyEvent.KEYCODE_BACK:

                /** 回退键 事件处理 优先级:视频播放全屏-网页回退-关闭页面 */

                if (customView != null) {

                    hideCustomView();
                }

                return true;

            default:

                return super.onKeyUp(keyCode, event);

        }

    }


    public class JsInteration {
        @JavascriptInterface
        public String vipbanlvUidCall() {
            JSONObject object = new JSONObject();
            String stringUId = SharePreferenceUtil.getinstance().getStringUId();
            Log.e(TAG, "========================vipbanlvUidCall:       stringUId：" + stringUId);
            try {
                object.put("vipbanlvUid", stringUId);
//                object.put("uidtoken","uidToken");
                object.put("version", Utils.getLocalVersionName());
                String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
                object.put("uidtoken", stringUIdToken);
                object.put("machine_type", "1");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return object.toString();
        }

        @JavascriptInterface
        public void vipbanlvPayCall(String data) {
            Log.e(TAG, "========================vipbanlvPayCall:       data：" + data);
            Gson gson = new Gson();
            alipayBean = gson.fromJson(data, AlipayBean.class);
            String stringUId = SharePreferenceUtil.getinstance().getStringUId();
            String title = alipayBean.getTitle() + "Android缘分值:" + stringUId+" 手机版本号："+Utils.getSystemVersion()+" 手机型号："+Utils.getSystemModel()+"品牌："+Utils.getDeviceBrand();
            alipayBean.setTitle(title);//加上Android和版本号
            Log.e("jp", "----------------------vipbanlvPayCall: " + data);
            doAlipay(alipayBean);
        }

        @JavascriptInterface
        public String vipbanlvPayCallback() {
            Log.e(TAG, "========================vipbanlvPayCallback:      ");
            JSONObject object = new JSONObject();
            try {
                object.put("orderId", String.valueOf(alipayBean.getOrderId()));
                object.put("payOrderId", String.valueOf(alipayBean.getOrderId()));
                object.put("payType", "alipay");
                String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
                object.put("version", Utils.getLocalVersionName());
                object.put("uidtoken", stringUIdToken);
                object.put("machine_type", "1");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return object.toString();
        }

        @JavascriptInterface
        public void vipbanlvCallData(String data) {
            Log.e(TAG, "========================vipbanlvCallData:       data：" + data);
            try {
                JSONObject obj = new JSONObject(data);
                String type = obj.optString("type", "");

                if (type.equals("2")) {

                    /**支付结束完成后*/

                    tv_complaint.setVisibility(View.VISIBLE);
                    tv_order.setVisibility(View.VISIBLE);
                } else if (type.equals("1")) {
                    webview.goBack();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    private void doAlipay(AlipayBean alipayBean) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        String orderInfo = getOrderInfo(alipayBean.getTitle(), alipayBean.getTitle(), String.valueOf(alipayBean.getPrice()));

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }


    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + alipayBean.getOrderId() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();

                        JSONObject object = new JSONObject();
                        try {
                            object.put("orderId", String.valueOf(alipayBean.getOrderId()));
                            object.put("payOrderId", String.valueOf(alipayBean.getOrderId()));
                            object.put("payType", "alipay");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            webview.evaluateJavascript("window.vipbanlvPayCallback(" + object + ")", new ValueCallback<String>() {

                                        @Override
                                        public void onReceiveValue(String value) {
//                                            tv_complaint.setVisibility(View.VISIBLE);
//                                            tv_order.setVisibility(View.VISIBLE);
                                            Log.e(TAG, "onReceiveValue: 支付宝支付完成：");

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                                webview.evaluateJavascript("window.vipbanlvCallData", new ValueCallback<String>() {

                                                            @Override
                                                            public void onReceiveValue(String value) {

                                                                Log.e(TAG, "onReceiveValue: 支付宝支付完成：onCreate------>value:" + value);
                                                                tv_complaint.setVisibility(View.VISIBLE);
                                                                tv_order.setVisibility(View.VISIBLE);
                                                            }
                                                        }
                                                );
                                            }


                                        }
                                    }
                            );
                        } else {
                            webview.loadUrl("javascript:window.vipbanlvPayCallback(" + object + ")");
                        }

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(activity, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    /**
     * ------------------------------alipay  end--------------------------------------
     */


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    RelativeLayout re;

    @Override
    protected void initView() {
        re = findViewById(R.id.re);
        webview = findViewById(R.id.webview);
        tv_order = findViewById(R.id.tv_order);
        tv_complaint = findViewById(R.id.tv_complaint);
        tv_order.setOnClickListener(this);
        tv_complaint.setOnClickListener(this);
        roundProgressBar = findViewById(R.id.roundProgressBar);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_order:
                presenter.gotoOrder(WebActivity.this);
                break;
            case R.id.tv_complaint:
                presenter.gotoAlipay(WebActivity.this);
                break;
            default:
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new WebViewPresenter(this);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void showNullLayout() {

    }

    @Override
    public void hideNullLayout() {

    }

    @Override
    public void showErrorLayout(View.OnClickListener listener) {

    }

    @Override
    public void hideErrorLayout() {

    }


    class MyWebViewClient extends WVJBWebViewClient {
        public MyWebViewClient(WebView webView) {
            // support js send
            super(webView, new WVJBWebViewClient.WVJBHandler() {

                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                }
            });


            enableLogging();//打开日志打印
            registerHandler("vipbanlvPayCall", new WVJBWebViewClient.WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    System.out.println("看看有什么2:" + data);

                    try {
                        JSONObject obj = new JSONObject(data.toString());
                        String orderid = obj.optString("orderId", "");
                        String money = obj.optString("price", "");

//							alipay(orderid,money);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    callback.callback("账号伴侣安卓端已经获取到信息!");
                }
            });
            registerHandler("vipbanlvUidCall", new WVJBWebViewClient.WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    System.out.println("看看有什么3:" + data);

                    JSONObject object = new JSONObject();
                    String stringUId = SharePreferenceUtil.getinstance().getStringUId();


                    try {
                        object.put("vipbanlvUid", stringUId);
                        object.put("machine_type", "1");
                        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
                        object.put("version", Utils.getLocalVersionName());
                        object.put("uidtoken", stringUIdToken);
                        callback.callback(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

            registerHandler("vipbanlvPayCallback", new WVJBWebViewClient.WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    System.out.println("看看有什么2:" + data);

                    JSONObject object = new JSONObject();
                    try {
//                        object.put("machine_type","1");
                        object.put("orderId", String.valueOf(alipayBean.getOrderId()));
                        object.put("payOrderId", String.valueOf(alipayBean.getOrderId()));
                        object.put("payType", "alipay");
                        object.put("payType", "alipay");
                        String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
                        object.put("version", Utils.getLocalVersionName());
                        object.put("uidtoken", stringUIdToken);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    callback.callback("账号伴侣安卓端已经获取到信息!");
                }
            });

            /**H5那边支付完成之后回调这个方法*/
            registerHandler("vipbanlvCallData", new WVJBWebViewClient.WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    System.out.println("看看有什么2:" + data);

                    try {
                        JSONObject obj = new JSONObject(data.toString());
                        String type = obj.optString("type", "");

                        if (type.equals("2")) {

                            /**支付结束完成后*/

                            tv_complaint.setVisibility(View.VISIBLE);
                            tv_order.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callback.callback("账号伴侣安卓端已经获取到信息!");
                }
            });

        }


        /*
            这里可以控制某个界面结束后的行为
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            //处理代码
            super.onPageFinished(view, url);
        }


        /*
            这里可以控制将要加载某个界面的行为
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e(TAG, "shouldOverrideUrlLoading:     ------------------------url：" + url);
            //处理代码 屏蔽网站

            if (url.contains("m.fkw.com") || url.contains("jzm.fkw.com")) {
                return true;
            }

            List<ParseBean> list = ParsingTools.FirstParseTool(url);
            if (list.size() >= 2) {
                if (list.get(0).getKey().equals("t")) {
                    String value = list.get(0).getValue();
                    int i = Integer.parseInt(value);
                    String mess = list.get(1).getValue();
                    switch (i) {
                        case 0:
                            jumpAppClassName(mess);
                            break;
                        case 1:
                            view.loadUrl(mess);
                            return super.shouldOverrideUrlLoading(view, url);
                        case 2:
                            jumpCheckApp(mess);
                            break;
                        case 3:
                            view.loadUrl(mess);
                            return super.shouldOverrideUrlLoading(view, url);
                        case 4:
                            view.loadUrl(mess);
                            return super.shouldOverrideUrlLoading(view, url);
                        case 6:
                            /**copy内容*/
                            copyMess(mess);
                            break;
                        case 9:
                            /**单个圈子*/
                            jumpSinpleCircle(mess);
                            break;
                        case 10:
                            /**单个人圈子*/
                            jumpSinplePersionCircle(mess);
                            break;
                        case 11:
                            /**备注*/
                            jumpCircleRemark(mess, list.get(2).getValue());
                            break;
                        case 8:
                            showTipDialog(list);
                            break;
                        default:
                            break;
                    }

                    return true;
                }
            }
            /**解析第一个url
             * */
            String[] urlstring = url.split("\\*1\\*\\*");
            if (urlstring.length > 1) {
                String secondurl = urlstring[1];
                view.loadUrl(secondurl);
                return super.shouldOverrideUrlLoading(view, url);
            }
            String[] urlstring1 = url.split("\\*3\\*\\*");
            if (urlstring1.length > 1) {
                String secondurl = urlstring1[1];
                view.loadUrl(secondurl);
                return super.shouldOverrideUrlLoading(view, url);
            }
            /**解析第二个sina_url
             * */
            String[] sinaurl = url.split("\\*2\\*\\*");

            if (sinaurl.length > 1) {
                String secondurl = sinaurl[1];
                if (sinaurl.length > 2) {
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", sinaurl[2]);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    String content;
                    if (sinaurl[1].contains("alipayqr://platformapi/startapp?saId=88886666")) {
                        content = "支付宝红包口令复制成功，去支付宝红包页面粘贴口令领红包吧！";
                    } else {

                        content = "福利内容复制成功，可以去对应APP粘贴获取福利。";
                    }
                    dialog = new PremissionsDialog(WebActivity.this, content, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            jumpCheckApp(secondurl);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    return true;
                } else {
                    jumpCheckApp(secondurl);
                    return true;
                }

            }


            /**
             * 解析用户中心和订单页面的url
             * */
            String[] spliturl = url.split("\\*\\*\\*");
            if (spliturl.length >= 2) {
                String name = spliturl[1];
                jumpAppClassName(name);
                return true;
            }

            if (url.contains("https://www.pgyer.com/CTcd")) {
                //一旦判断出是要下载更新，则弹出下载对话框并不进行页面跳转，不进行页面跳转传true
                return true;
            } else {
                String[] split;
                split = url.split("\\*8\\*\\*");
                if (split.length >= 2) {

                    String a = split[1];
                    String[] split1 = a.split("@");
                    String first = split1[0];
                    String content = "";
                    for (int i = 1; i < split1.length; i++) {
                        content = content + split1[i];
                    }
                    try {
                        String result = URLDecoder.decode(content, "UTF-8");//用Android自带的URLDecoder解析成中文
                        result = result + "397zx.cASD!@#";
                        String contentMD5 = MD5Util.getStringMD5(result);
                        String substring = contentMD5.substring(11, 24);
                        if (substring.equals(first)) {
                            String type = split1[1];
                            if (type.equals("4")) {
                                /**
                                 * 跳转支付宝
                                 * */
                                jumpAlipay(URLDecoder.decode(split1[2], "UTF-8"));
                                return true;
                            } else if (type.equals("5")) {
                                /**
                                 * 天猫或者淘宝
                                 * */
                                jumpTianMao(URLDecoder.decode(split1[3], "UTF-8"));
                                return true;
                            } else if (type.equals("3")) {
                                /**
                                 * 播放视频
                                 * */
                                view.loadUrl(split1[2]);
                                return super.shouldOverrideUrlLoading(view, url);
                            } else {
                                Log.e(TAG, "shouldOverrideUrlLoading:     type不是3，4，5的任何一种");
                            }
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


                } else {

                    /**
                     * 兼容老版本
                     * */
                    split = url.split("\\*4\\*\\*");
                    if (split.length >= 2) {

                        String content = split[1];
                        jumpAlipay(content);
                        return true;
                    }

                }
                Log.e(TAG, "shouldOverrideUrlLoading:    兼容老版本      不需要解析url");
                return super.shouldOverrideUrlLoading(view, url);
            }
//            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    CheckUpLoadDialog mTipDialog;


    private void showTipDialog(List<ParseBean> list) {

        String content = list.get(3).getValue();
        mTipDialog = new CheckUpLoadDialog(WebActivity.this, "温馨提示", content, "取消", "去看看", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_cancel:

                        mTipDialog.dismiss();
                        break;
                    case R.id.tv_submit:
                        String t1 = list.get(1).getKey();
                        String t1_value = list.get(1).getValue();
                        if (t1.equals("t1")) {
                            String value3 = list.get(2).getValue();
                            if (t1_value.equals("0")) {
                                /**重新走一遍解析*/
//                                List<ParseBean> list1 = ParsingTools.FirstParseTool(value3);
//                                webview.loadUrl(value3);
                                ParsingTools tools=new ParsingTools();
                                tools.SecondParseTool(WebActivity.this,value3);
                            } else if (t1_value.equals("1")) {
                               /**分享文本类型*/
                                ARouter.getInstance()
                                        .build("/activity/ShareWechat")
                                        .withString("copystring", value3)
                                        .navigation();
                            } else if (t1_value.equals("2")) {
                               /** 进入圈子备注页面*/
                                jumpCircleRemark(t1_value, value3);
                            } else {
                                webview.loadUrl(value3);
                            }
                        }
                        mTipDialog.dismiss();
                        break;
                }
            }
        });
        mTipDialog.show();
    }

    private void jumpCircleRemark(String mess, String value) {
        ARouter.getInstance().build("/activity/WebUserActivity")
                .withInt("tag", 4)
                .withString("cricleid", value)
                .withString("produceid", mess)
                .navigation();
    }

    private void jumpSinpleCircle(String mess) {
        ARouter.getInstance()
                .build("/activity/CommonActivity")
                .withInt("tag", 0)
                .withString("cricleid", mess)
                .navigation();
    }
    private void jumpSinplePersionCircle(String mess) {
        ARouter.getInstance()
                .build("/activity/WebUserActivity")
                .withInt("tag", 1)
                .withString("cricleid", mess)
                .navigation();
    }
    private void copyMess(String mess) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", mess);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastUtil.showToast("复制成功");
    }

    private void jumpAppClassName(String name) {
        if (name.equals("VipUserCentreViewController")) {
            /**
             * 用户中心
             * */
            ARouter.getInstance()
                    .build("/activity/WebUserActivity")
                    .withInt("tag", 0)
                    .navigation();

        } else if (name.equals("PurchaseRecordViewController")) {
            /**
             * 我的订单
             * */
            Intent intent = new Intent(WebActivity.this, OrderListActivity.class);
            startActivity(intent);
        } else if (name.equals("RecommendViewController")) {
            /**
             * 跳转微信分享
             * */
            jumpShareWeChat();
        } else if (name.equals("ChatMessageListController")) {
            /**消息列表*/

            ARouter.getInstance()
                    .build("/activity/WebUserActivity")
                    .withInt("tag", 2)
                    .navigation();
        }else if (name.equals("ACMomentsViewController")){
             /**进入圈子*/
             ARouter.getInstance()
                     .build("/user/UserInformation")
                     .withInt("tag",14)
                     .navigation();
        }
    }

    private void jumpCheckApp(String secondurl) {

        if (secondurl.contains("sinaweibo")) {
            if (isWeiboInstalled(activity)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装微博APP无法查看相关内容");
            }
        } else if (secondurl.contains("alipay")) {
            if (isWeiboInstalled(activity)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装支付宝APP无法查看相关内容");
            }
        } else if (secondurl.contains("alipayqr")) {
            if (isWeiboInstalled(activity)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装支付宝APP无法查看相关内容");
            }
        } else if (secondurl.contains("weixin")) {
            if (isWeiboInstalled(activity)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装微信APP无法查看相关内容");
            }
        } else if (secondurl.contains("mqq")) {
            if (isWeiboInstalled(activity)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装QQ无法查看相关内容");
            }
        } else if (secondurl.contains("taobao")) {
            if (isWeiboInstalled(activity)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装淘宝APP无法查看相关内容");
            }
        } else if (secondurl.contains("tmall")) {
            if (isWeiboInstalled(activity)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装天猫APP无法查看相关内容");
            }
        } else if (secondurl.contains("snssdk1128")) {
            if (isWeiboInstalled(activity)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装抖音APP无法查看相关内容");
            }
        } else if (secondurl.contains("gifshow")) {
            if (isWeiboInstalled(activity)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装快手APP无法查看相关内容");
            }
        } else if (secondurl.contains("pinduoduo")) {
            if (isWeiboInstalled(activity)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装拼多多APP无法查看相关内容");
            }
        } else if (secondurl.contains("openApp.jdMobile")) {
            if (isWeiboInstalled(activity)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(secondurl));
                startActivity(intent);

            } else {
                ToastUtil.showToast("没有安装京东APP无法查看相关内容");
            }
        }

    }


    public boolean isWeiboInstalled(Context context) {
        PackageManager pm;
        if ((pm = context.getApplicationContext().getPackageManager()) == null) {
            return false;
        }
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo info : packages) {
            String name = info.packageName.toLowerCase(Locale.ENGLISH);
            if ("com.sina.weibo".equals(name)) {
                /**微博*/
                return true;
            } else if ("com.tmall.wireless".equals(name)) {
                /**天猫*/
                return true;
            } else if ("com.taobao.taobao".equals(name)) {
                /**淘宝*/
                return true;
            } else if ("com.eg.android.AlipayGphone".equals(name)) {
                /**支付宝*/
                return true;
            } else if ("com.jingdong.app.mall".equals(name)) {
                /**京东*/
                return true;
            } else if ("com.smile.gifmaker".equals(name)) {
                /**快手*/
                return true;
            } else if ("com.tencent.mobileqq".equals(name)) {
                /**qq*/
                return true;
            } else if ("com.tencent.mm".equals(name)) {
                /**微信*/
                return true;
            } else if ("com.xunmeng.pinduoduo".equals(name)) {
                /**拼多多*/
                return true;
            } else if ("com.ss.android.ugc.aweme".equals(name)) {
                /**抖音*/
                return true;
            }
        }
        return false;
    }


    private void jumpShareWeChat() {
        String share = "《小树林》原来还可以这样！\n" +
                "我在这里买各种东东方便又便宜，你也可以试试！\n" +
                "小树林APP各大视频网站会员一个月只要几块钱。点击链接即可试用：http://www.vipbanlv.com/www_v1\n" +
                "\n" + "\uD83D\uDC8B" +
                "安卓手机可以点击这个链接下载app：http://dwz.cn/86EfALIU" +
                "\n" +
                "\n" + "\uD83D\uDC8B" +
                "苹果手机可以点击这个链接下载app：http://dwz.cn/lqrDxatM";
        ARouter.getInstance()
                .build("/activity/ShareWechat")
                .withString("copystring", share)
                .navigation();
    }


    public String getAppProcessName(Context context) {
        //当前应用pid
        final PackageManager packageManager = getPackageManager();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // get all apps
        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        String name = "";
        for (int i = 0; i < apps.size(); i++) {
            name = apps.get(i).activityInfo.packageName;
            if (name.equals("com.tmall.wireless")) {
                return name;
            } else if (name.equals("com.taobao.taobao")) {
                return name;
            }
        }
        return name;
    }


    private void jumpTianMao(String message) {

        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", message);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        //跳转支付宝
        try {
            PackageManager packageManager
                    = this.getApplicationContext().getPackageManager();
            String appProcessName = getAppProcessName(WebActivity.this);
            Intent intent = packageManager.getLaunchIntentForPackage(appProcessName);
            startActivity(intent);
        } catch (Exception e) {
            String url = "https://ds.tmall.com/?from=mobileweb";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }


    private void jumpAlipay(String message) {

        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", message);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        //跳转支付宝
        try {
            PackageManager packageManager
                    = this.getApplicationContext().getPackageManager();
            Intent intent = packageManager.
                    getLaunchIntentForPackage("com.eg.android.AlipayGphone");
            startActivity(intent);
        } catch (Exception e) {
//            String url = "https://ds.alipay.com/?from=mobileweb";
            String url= "http://render.2ljjhsny6.com/p/f/jfxb4alj/pages/receive-redpacket/index.html?__webview_options__=ttb%253Dauto&sceneCode=C2C_APP_NEW&shareChannel=QRCode&shareUserId=2088022909835279&sharedUserId=&sign=mQqpzaSriBZvy7KYsUwBtK1xwcqq%2BuSp3IF1COYxo1M%3D&abFrom=C2C_APP_NEWundefined";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FX_FOCUS_NAVIGATION_UP);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FX_FOCUS_NAVIGATION_UP);
                return true;
            case KeyEvent.KEYCODE_BACK:
                if (webview.canGoBack()) {
                    tv_complaint.setVisibility(View.GONE);
                    tv_order.setVisibility(View.GONE);
                    webview.goBack();
                } else {
                    Log.e(TAG, "onKeyDown:           清理缓存了，结束webview啦");
                    //此处写入点击确认退出时的方法
                    WebActivity.this.finish();
                    //清理缓存
                    webview.clearCache(true);
                }
                return true;
        }
        return true;
    }


}
