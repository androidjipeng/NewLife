package com.xiaoshulin.vipbanlv.user;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.activity.ShareWechatActivity;
import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.CostomFieldBean;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.bean.SupplyAccountInforBean;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.jpush.LocalBroadcastManager;
import com.xiaoshulin.vipbanlv.popup.JpushPopupwindow;
import com.xiaoshulin.vipbanlv.user.presenter.AccountInformationPresenter;
import com.xiaoshulin.vipbanlv.user.view.IAccountInforView;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.TitleBar;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 用户中心  账号详情页面
 */
@Route(path = "/user/AccountInformationActivity")
public class AccountInformationActivity extends BaseMVPActivity implements View.OnClickListener, IAccountInforView {
    private static final String TAG = "AccountInformationActiv";
    private AccountInformationPresenter presenter;

    TitleBar titlebar;
    TextView money_value, money_value1, money_value2, money_value3, money_value4;
    Button btn;

    @Autowired
    public String title;
    @Autowired
    public String aid;

    Context context;
    CheckUpLoadDialog premissdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        context=this;
        registerMessageReceiver();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_account_information;
    }

    @Override
    protected void initView() {
        titlebar = findViewById(R.id.titlebar);
        money_value = findViewById(R.id.money_value);
        money_value1 = findViewById(R.id.money_value1);
        money_value2 = findViewById(R.id.money_value2);
        money_value3 = findViewById(R.id.money_value3);
        money_value4 = findViewById(R.id.money_value4);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new AccountInformationPresenter(this);
    }

    @Override
    protected void initData() {
        titlebar.setCenterText(title);
        presenter.getAccountInformation();

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
            case R.id.btn:

//                jumpAlipay(ToastUtil.zhifubao());
                  ARouter.getInstance()
                          .build("/activity/OrderInformationActivity")
                          .withInt("tag",0)
                          .navigation();

                break;
        }
    }

    private void jumpAlipay(String message) {

        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", message);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        //跳转支付宝
        try {
            PackageManager packageManager
                    =getApplicationContext().getPackageManager();
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


    @Override
    public String getaid() {
        return aid;
    }

    @Override
    public void accountInformation(SupplyAccountInforBean bean) {

        money_value.setText(bean.getDays()+"天");
//        money_value1.setText();
        money_value2.setText(bean.getWechat());
        money_value3.setText(bean.getUsername());
        money_value4.setText(bean.getPassword());
        //如果
        if (!bean.getDays().equals("0"))
        {
            btn.setText("无法使用VIP服务想提前修改密码");
        }
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
                        Log.e(TAG, "========onReceive: ");
//                        ToastUtil.showToast("Pushtype为14，去要验证码页面，待开发...");
//                        saveData(jPushBean);
                    }
                }
            } catch (Exception e) {
            }
        }
    }


    @SuppressLint("NewApi")
    private void setCostomMsg(String ALERT,String content) {
        window1 = new JpushPopupwindow(AccountInformationActivity.this,ALERT,content);
        window1.showAtLocation(findViewById(R.id.ll_accountinformation),
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

}
