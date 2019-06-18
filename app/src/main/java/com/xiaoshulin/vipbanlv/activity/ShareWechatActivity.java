package com.xiaoshulin.vipbanlv.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaoshulin.vipbanlv.MainActivity;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.WebActivity;
import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.CostomFieldBean;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.jpush.LocalBroadcastManager;
import com.xiaoshulin.vipbanlv.popup.JpushPopupwindow;
import com.xiaoshulin.vipbanlv.presenter.ShareWechatPresenter;
import com.xiaoshulin.vipbanlv.utils.Constants;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.ShareUtils;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.IShareWeChatView;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Route(path = "/activity/ShareWechat")
public class ShareWechatActivity extends BaseMVPActivity implements IShareWeChatView, View.OnClickListener {
    private static final String TAG = "ShareWechatActivity";
    Context context;
    @Autowired
    public String copystring;

    String test="sdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdf";

    int type;
    private ShareWechatPresenter presenter;
    private ImageView share_bg;
    private ImageView share_wechat, share_friend;
    private TextView tv_share_wechat, tv_share_friend;
    private IWXAPI api;
    CheckUpLoadDialog premissdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        context=this;
        registerMessageReceiver();
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID,false);
        api.registerApp(Constants.APP_ID);
        shareMessage();

    }

    private void shareMessage() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_wechat;
    }

    @Override
    protected void initView() {
        share_bg = findViewById(R.id.share_bg);
        share_wechat = findViewById(R.id.share_wechat);
        share_friend = findViewById(R.id.share_friend);
        tv_share_wechat = findViewById(R.id.tv_share_wechat);
        tv_share_friend = findViewById(R.id.tv_share_friend);

        share_friend.setOnClickListener(this);
        share_wechat.setOnClickListener(this);
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new ShareWechatPresenter(this);
    }

    @Override
    protected void initData() {
        String times = Utils.getTimestyle();
        String mum = times.substring(5);
       int num=Integer.parseInt(mum);
        if (num % 2 == 0) {
            share_bg.setImageResource(R.drawable.ic_share1);
            share_wechat.setImageResource(R.drawable.ic_orange_share);
            share_friend.setImageResource(R.drawable.ic_orange_share_friend);
            tv_share_wechat.setTextColor(getResources().getColor(R.color.orange));
            tv_share_friend.setTextColor(getResources().getColor(R.color.orange));
        } else {
            share_bg.setImageResource(R.drawable.ic_share2);
            share_wechat.setImageResource(R.drawable.ic_red_share);
            share_friend.setImageResource(R.drawable.ic_red_share_friend);
            tv_share_wechat.setTextColor(getResources().getColor(R.color.red));
            tv_share_friend.setTextColor(getResources().getColor(R.color.red));
        }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_wechat:
                type=SendMessageToWX.Req.WXSceneSession;
                send_text(copystring,type);
                break;
            case R.id.share_friend:
                type= SendMessageToWX.Req.WXSceneTimeline;
                send_text(copystring,type);
                break;
            default:
                break;
        }
    }

    private void send_text(String text, int type)
    {


        WXTextObject textObj = new WXTextObject();
        textObj.text = text;


        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.title = "小树林";
        msg.description = text;


        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = type;
        api.sendReq(req);
//        finish();
    }



    private static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    /**      start监听光广播*/
    private JpushPopupwindow window1;
    @Override
    protected void onDestroy() {

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
                    setCostomMsg(ALERT,jPushBean.getContent());
                    /**
                     * 作弹框区分
                     * */
                    if (jPushBean.getPushtype().equals("2"))
                    {
                        premissdialog=new CheckUpLoadDialog(context, jPushBean.getALERT(),jPushBean.getContent(),"取消","去反馈", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId())
                                {
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

                    }else if (jPushBean.getPushtype().equals("3"))
                    {
                        premissdialog=new CheckUpLoadDialog(context, "通知","您的账号没有通过审核！","取消","去反馈", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId())
                                {
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
                    }else if (jPushBean.getPushtype().equals("14"))
                    {
//                        ToastUtil.showToast("Pushtype为14，去要验证码页面，待开发...");
                        Log.e(TAG, "========onReceive: ");
//                        saveData(jPushBean);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private void saveData(JPushBean jPushBean) {
        new DataBaseUtils().insertChatMessageData(context,jPushBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean)
                        {
                            new DataBaseUtils().insertMessageListData(context,jPushBean)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {
                                            if (aBoolean)
                                            {
                                                String customfield = jPushBean.getCustomfield();
                                                Gson gson=new Gson();
                                                CostomFieldBean costomFieldBean = gson.fromJson(customfield, CostomFieldBean.class);
                                                ARouter.getInstance()
                                                        .build("/activity/ChatMessageActivity")
                                                        .withString("titleName",costomFieldBean.getNickName())
                                                        .withString("messageSectionid",costomFieldBean.getMessageSection())
                                                        .navigation();
                                            }else {
                                                ToastUtil.showToast("插入--聊天列表--失败");
                                            }
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {

                                        }
                                    });

                        }else {
                            ToastUtil.showToast("插入--聊天记录表---的失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "插入异常-----------accept: "+throwable);
                    }
                });
    }

    @SuppressLint("NewApi")
    private void setCostomMsg(String ALERT,String content) {
        window1 = new JpushPopupwindow(ShareWechatActivity.this,ALERT,content);
        window1.showAtLocation(findViewById(R.id.cnl),
                Gravity.CENTER | Gravity.TOP,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()));
        window1.showAsDropDown(findViewById(R.id.cnl),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()),
                Gravity.CENTER | Gravity.TOP);
        window1.update();

    }
    /**   end*/
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
            String url = "https://ds.alipay.com/?from=mobileweb";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }
}
