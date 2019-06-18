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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.CostomFieldBean;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.bean.SupplyAccountInforBean;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.dialog.WarningDialog;
import com.xiaoshulin.vipbanlv.jpush.LocalBroadcastManager;
import com.xiaoshulin.vipbanlv.popup.JpushPopupwindow;
import com.xiaoshulin.vipbanlv.presenter.SupplyAccountPresenter;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.TitleBar;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.view.ISupplyAccountView;

import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Route(path = "/activity/SupplyAccountActivity")
public class SupplyAccountActivity extends BaseMVPActivity implements ISupplyAccountView, View.OnClickListener {
    private static final String TAG = "SupplyAccountActivity";
    private Context context;
    private SupplyAccountPresenter presenter;

    private TitleBar titlebar;

    private EditText supply_value, supply_value1, supply_value2, supply_value3;

    private Button supply_btn;

    @Autowired
    public String pid;
    @Autowired
    public String title;
    @Autowired
    public String aid;
    @Autowired
    public String tag;
    PremissionsDialog dialog;

    WarningDialog warningDialog;
    CheckUpLoadDialog premissdialog;

    String oldPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        registerMessageReceiver();

        context = this;
        Tip();

    }

    PremissionsDialog dialog1;
    private void Tip() {
         dialog1=new PremissionsDialog(context, getResources().getString(R.string.tip), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });
        dialog1.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_supply_account;
    }

    @Override
    protected void initView() {
        titlebar = findViewById(R.id.titlebar);
        supply_value = findViewById(R.id.supply_value);
        supply_value1 = findViewById(R.id.supply_value1);
        supply_value2 = findViewById(R.id.supply_value2);
        supply_value3 = findViewById(R.id.supply_value3);
        supply_value3.setKeyListener(listener);
        supply_btn = findViewById(R.id.supply_btn);
        supply_btn.setOnClickListener(this);

    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new SupplyAccountPresenter(this);
    }

    @Override
    protected void initData() {

        titlebar.setCenterText(title);
        if (tag.equals("1")) {
            presenter.getAccountInformation();
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
            case R.id.supply_btn:
                submitAccountdAata();
                break;
            default:
                break;
        }
    }

    private void submitAccountdAata() {

        /**提交账号数据*/

        String days = supply_value.getText().toString();
        boolean isday = false;
        if (!days.equals("")) {
            isday = Integer.parseInt(days) >= 1 && Integer.parseInt(days) <= 365;
        }

        String psd = "vipbanlv.com#"+supply_value3.getText().toString();
        boolean issmall = false;
        boolean isbig = false;
        String substring = "";
        if (psd.length() > 13) {

            substring = psd.substring(0, 13);
            String num = psd.substring(13);
            boolean integer1 = isInteger(num);
            if (integer1) {
                Integer integer = Integer.parseInt(num);
                issmall = integer >= 100;
                isbig = integer <= 999;
            }
        }

        if (days.equals("") || !isday) {
            dialog = new PremissionsDialog(context, "请按天数1~365天要求填写输入", new listener());
            dialog.show();
        } else if (supply_value1.getText().toString().equals("")) {
            dialog = new PremissionsDialog(context, "请填写您的微信号，用于提现安全验证。", new listener());
            dialog.show();
        } else if (supply_value2.getText().toString().equals("")) {
            dialog = new PremissionsDialog(context, "请填写账号", new listener());
            dialog.show();
        } else if (psd.equals("") || (!substring.contains("vipbanlv.com#")) || !(issmall && isbig)) {
            dialog = new PremissionsDialog(context, "密码请按照vipbanlv.com#开头加三位数字结尾的格式填写。", new listener());
            dialog.show();

        } else {
            if (tag.equals("1")) {
                if (oldPassword.equals(supply_value3.getText().toString())) {
                    dialog = new PremissionsDialog(context, "请修改密码后再提交哦", new listener());
                    dialog.show();
                }else {
                    warningDialog = new WarningDialog(context, "如果您提交的账号和密码有误，或不是会员账号，将扣除您1V的信用等级！", "重新检查", "同意确认", new checkListener());
                    warningDialog.show();
                }
            } else {
                warningDialog = new WarningDialog(context, "如果您提交的账号和密码有误，或不是会员账号，将扣除您1V的信用等级！", "重新检查", "同意确认", new checkListener());
                warningDialog.show();
            }
        }
    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    class listener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    }

    class checkListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_cancel:
                    warningDialog.dismiss();
                    break;
                case R.id.tv_submit:
                    warningDialog.dismiss();
                    presenter.submit();
                    break;

            }
        }
    }

    @Override
    public void submitAccountSuccess() {
        /**提交账号成功*/
        ToastUtil.showToast("账号提交成功");
        finish();
    }

    @Override
    public String getpid() {
        return pid;
    }

    @Override
    public String getaid() {
        if (aid == null) {
            aid = "";
        }
        return aid;
    }

    @Override
    public String getusername() {
        return supply_value2.getText().toString();
    }

    @Override
    public String getpassword() {
        return "vipbanlv.com#"+supply_value3.getText().toString();
    }

    @Override
    public String getdays() {
        return supply_value.getText().toString();
    }

    @Override
    public String getwechat() {
        return supply_value1.getText().toString();
    }


    @Override
    public void accountInformation(SupplyAccountInforBean bean) {

        supply_value.setText(bean.getDays());
        supply_value1.setText(bean.getWechat());
        supply_value2.setText(bean.getUsername());
        String pass = bean.getPassword();
        oldPassword=pass.substring(13);
        supply_value3.setText(oldPassword);
    }


    /**
     * start监听光广播
     */
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
        window1 = new JpushPopupwindow(SupplyAccountActivity.this, ALERT, content);
        window1.showAtLocation(findViewById(R.id.ll_supply),
                Gravity.CENTER | Gravity.TOP,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()));
        window1.showAsDropDown(findViewById(R.id.cnl),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()),
                Gravity.CENTER | Gravity.TOP);
        window1.update();

    }

    /**
     * end
     */

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


    KeyListener listener = new NumberKeyListener() {

        /**
         * @return ：返回哪些希望可以被输入的字符,默认不允许输入
         */
        @Override
        protected char[] getAcceptedChars() {
            char[] chars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'X'};
            return chars;
//            return new char[0];
        }

        /**
         * 0：无键盘,键盘弹不出来
         * 1：英文键盘
         * 2：模拟键盘
         * 3：数字键盘
         *
         * @return
         */
        @Override
        public int getInputType() {
            return 3;
        }
    };
}
