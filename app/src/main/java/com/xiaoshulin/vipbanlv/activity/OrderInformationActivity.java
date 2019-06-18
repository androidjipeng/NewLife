package com.xiaoshulin.vipbanlv.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.bean.CostomFieldBean;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.OrderInformationDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.jpush.LocalBroadcastManager;
import com.xiaoshulin.vipbanlv.popup.JpushPopupwindow;
import com.xiaoshulin.vipbanlv.presenter.OrderInformPresenter;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;

import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.IOrderInformView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Route(path = "/activity/OrderInformationActivity")
public class OrderInformationActivity extends BaseMVPActivity implements IOrderInformView, View.OnClickListener {
    private static final String TAG = "OrderInformationActivit";
    private OrderInformationDialog dialog;
    private OrderInformPresenter presenter;

    private TextView big_inform_title, time, custom_service, account, password, btn_copy1, btn_copy2, btn_copy3;
    private Button btn_code, complaint, complaintwechat, complaintqq, btn_code1;
    private RelativeLayout rel1, rel2, rel3, rel4;

    @Autowired
    public String Name;
    @Autowired
    public String Dayend;
    @Autowired
    public String Wechat;
    @Autowired
    public String Orderid;
    @Autowired
    public String Password;
    @Autowired
    public String pushuid;
    @Autowired
    public String pushwechat;
    @Autowired
    public int tag;

    public String pushuid1;
    private Context context;

    private CheckUpLoadDialog premissdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        context = this;
        registerMessageReceiver();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_information;
    }

    @Override
    protected void initView() {
        big_inform_title = findViewById(R.id.big_inform_title);
        time = findViewById(R.id.time);
        custom_service = findViewById(R.id.custom_service);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);

        btn_copy1 = findViewById(R.id.btn_copy1);
        btn_copy2 = findViewById(R.id.btn_copy2);
        btn_copy3 = findViewById(R.id.btn_copy3);
        btn_code = findViewById(R.id.btn_code);
        complaint = findViewById(R.id.complaint);
        complaintwechat = findViewById(R.id.complaintwechat);
        complaintqq = findViewById(R.id.complaintqq);

        btn_code1 = findViewById(R.id.btn_code1);


        rel1 = findViewById(R.id.rel1);
        rel2 = findViewById(R.id.rel2);
        rel3 = findViewById(R.id.rel3);
        rel4 = findViewById(R.id.rel4);


        btn_copy1.setOnClickListener(this);
        btn_copy2.setOnClickListener(this);
        btn_copy3.setOnClickListener(this);

        btn_code.setOnClickListener(this);
        complaint.setOnClickListener(this);
        complaintwechat.setOnClickListener(this);
        complaintqq.setOnClickListener(this);

        btn_code1.setOnClickListener(this);

    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new OrderInformPresenter(this);
    }

    @Override
    protected void initData() {
        /*0是小树林客服，1是正常的订单详情*/
        if (tag == 0) {
            big_inform_title.setText(Name);
            rel1.setVisibility(View.GONE);
            rel2.setVisibility(View.GONE);
            rel3.setVisibility(View.GONE);
            rel4.setVisibility(View.GONE);
            btn_code1.setVisibility(View.GONE);
            btn_code.setText("小树林客服");
        } else if (tag == 1) {
            big_inform_title.setText(Name);
            time.setText(Dayend);
            custom_service.setText(Wechat);
            account.setText(Orderid);
            password.setText(Password);
            btn_code1.setVisibility(View.VISIBLE);
            if (Dayend.equals("已过期")) {
                btn_code.setText("已到期，点我再次购买");
            }
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
            case R.id.btn_copy1:
                String text1 = custom_service.getText().toString();
                copyText(text1);
                break;
            case R.id.btn_copy2:
                String text2 = account.getText().toString();
                copyText(text2);
                break;
            case R.id.btn_copy3:
                String text3 = password.getText().toString();
                copyText(text3);
                break;
            case R.id.btn_code:
                if (Dayend.equals("已过期")) {
                    ARouter.getInstance()
                            .build("/activity/WebActivity")
                            .withString("url", "http://www.vipbanlv.com")
                            .navigation();

                } else {
                    content = Name + "\n" + "到期时间:" + Dayend + "\n" + "微信客服:" + Wechat + "\n" + "账号:" + Orderid + "\n" + "密码:" + Password;
                    /**
                     * 发送验证码
                     * */
                    verifyStoragePermissions(OrderInformationActivity.this);
                }

                break;
            case R.id.complaint:
                complaintQA();
                break;
            case R.id.complaintwechat:
                complaintWeChat();
                break;
            case R.id.complaintqq:
                complaintQQ();
                break;
            case R.id.btn_code1:
                /*投诉*/

                content = Name + "(" + pushuid + ")(" + pushwechat + ")" + "\n" + "到期时间:" + Dayend + "\n" + "微信客服:" + Wechat + "\n" + "账号:" + Orderid + "\n" + "密码:" + Password;
                pushuid1 = "1374";
                presenter.sendInformation1();
                break;
            default:
                break;

        }
    }

    private void complaintQQ() {
        String url = "mqqwpa://im/chat?chat_type=wpa&uin=3242252282";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    private void complaintWeChat() {
        String mess = "xiaoshulinapp";
        copyText(mess);
        jumpWechat();
    }

    private void jumpWechat() {
        Intent intent = new Intent();
        ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(cmp);
        startActivity(intent);

    }

    private void complaintQA() {

        jumpAlipay(ToastUtil.zhifubao());
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
            String url = "https://ds.alipay.com/?from=mobileweb";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

    private void copyText(String text) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);

        ToastUtil.showToast("复制成功");

    }

    String content;

    @Override
    public String getcontent() {
        return content;
    }

    @Override
    public String getpushuid() {

        return pushuid;
    }

    @Override
    public String getpushuid1() {
        return pushuid1;
    }

    JPushBean savebean;

    @Override
    public void show(JPushBean bean) {
        /**
         * 显示提示消息
         * */
        savebean = bean;
        saveSendData(bean);

    }


    @Override
    protected void onStop() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        super.onStop();

    }


    private class WarningListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_know:
                    String customfield = savebean.getCustomfield();
                    Gson gson = new Gson();
                    CostomFieldBean costomFieldBean = gson.fromJson(customfield, CostomFieldBean.class);
                    ARouter.getInstance()
                            .build("/activity/ChatMessageActivity")
                            .withString("titleName", costomFieldBean.getNickName())
                            .withString("messageSectionid", costomFieldBean.getMessageSection())
                            .navigation();
                    dialog.dismiss();
                    break;
                default:
                    break;
            }
        }
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
                                        String message = "#吱口令#长按复制此条消息，打开支付宝即可去小树林APP支付宝客服反馈01MmLE56xb";
                                        jumpAlipay(message);
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


    private void saveSendData(JPushBean jPushBean) {
        new DataBaseUtils().insertChatMessageData(context, jPushBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            new DataBaseUtils().insertMessageListData(context, jPushBean)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<Boolean>() {
                                        @Override
                                        public void accept(Boolean aBoolean) throws Exception {
                                            if (aBoolean) {

                                                dialog = new OrderInformationDialog(OrderInformationActivity.this, "温馨提示", "要验证请求已发出，五分钟内验证码都有效哦！请注意查收推送消息，如果多次请求都没有回复，请点击账号投诉按钮去小树林支付宝客服反馈", new WarningListener());
                                                dialog.show();

                                            } else {
                                                ToastUtil.showToast("插入--聊天列表--失败");
                                            }
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {

                                        }
                                    });

                        } else {
                            ToastUtil.showToast("插入--聊天记录表---的失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "插入异常-----------accept: " + throwable);
                    }
                });
    }


    @SuppressLint("NewApi")
    private void setCostomMsg(String ALERT, String content) {
        window1 = new JpushPopupwindow(OrderInformationActivity.this, ALERT, content);
        window1.showAtLocation(findViewById(R.id.lll),
                Gravity.CENTER | Gravity.TOP,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()));
        window1.showAsDropDown(findViewById(R.id.lll),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()),
                Gravity.CENTER | Gravity.TOP);
        window1.update();

    }

    /**
     * end
     */
    PremissionsDialog premissionsDialog;


    class dialogListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_ok:

                    premissionsDialog.dismiss();
                    break;
            }

        }
    }


    class premissDialogListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_ok:
                    premissionsDialog.dismiss();
                    ActivityCompat.requestPermissions(OrderInformationActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                    break;
            }

        }
    }

    public void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                premissionsDialog = new PremissionsDialog(activity, "为了让对方知道您买了他的账号，需要获得您购买的账号信息，需要您的权限，请同意。", new premissDialogListener());
                premissionsDialog.show();

            } else {
                String currentHHmmTime = Utils.getCurrentHHmmTime();
                boolean isbetween = Utils.hourMinuteBetween(currentHHmmTime, "08:00", "23:00");
                if (isbetween) {
                    if (tag == 0) {
                        /*给客服*/
                        presenter.sendServiceInformation();
                    } else {
                        /*给账号提供方*/
                        presenter.sendInformation();
                    }
                } else {
                    premissionsDialog = new PremissionsDialog(activity, "客服服务时间早8:00到晚11:00" + "/n" + "如果有什么疑问点击客服反馈。", new dialogListener());
                    premissionsDialog.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    /**
     * 保存文件到本机存储
     *
     * @param toSaveString
     */
    public void savefile(String toSaveString, String uidtoken) {
        Log.e(TAG, "savefile:       ---------------> toSaveString:" + toSaveString);
        int num = Integer.parseInt(toSaveString) + 214;
        Log.e(TAG, "savefile:       ---------------> num:" + num);
        int num1 = num * 3;
        Log.e(TAG, "savefile:       ---------------> num1:" + num1);
        String a = "ab3478re109qw" + num1 + "scd66487ab3t";
        Log.e(TAG, "savefile:       ---------------> a:" + a);
        a = a + "*" + uidtoken;
        File file = new File(Environment.getExternalStorageDirectory(), "/android1/weixinxslapi.txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try {
            FileOutputStream stream = new FileOutputStream(file.getAbsolutePath());
            byte[] buf = a.getBytes();
            stream.write(buf);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // 权限被用户同意，可以去放肆了。
                Log.e("jp", "onRequestPermissionsResult:  权限被用户同意，可以去放肆了");
                String stringUId = SharePreferenceUtil.getinstance().getStringUId();
                String uidtoken = SharePreferenceUtil.getinstance().getStringUIdToken();
                savefile(stringUId, uidtoken);

            } else {

                // 权限被用户拒绝了，洗洗睡吧。
                Log.e("jp", "onRequestPermissionsResult:  权限被用户拒绝了，洗洗睡吧");

            }
        }
    }


}
