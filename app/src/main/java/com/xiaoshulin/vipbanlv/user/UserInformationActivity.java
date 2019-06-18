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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

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
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.fragment.CircleFragment;
import com.xiaoshulin.vipbanlv.fragment.HomeFragment;
import com.xiaoshulin.vipbanlv.fragment.OrderFragment;
import com.xiaoshulin.vipbanlv.fragment.UserFragment;
import com.xiaoshulin.vipbanlv.jpush.LocalBroadcastManager;
import com.xiaoshulin.vipbanlv.popup.JpushPopupwindow;
import com.xiaoshulin.vipbanlv.presenter.UserInformationPresenter;
import com.xiaoshulin.vipbanlv.user.frament.LuckValueFragment;
import com.xiaoshulin.vipbanlv.user.frament.UserListInforFragment;
import com.xiaoshulin.vipbanlv.user.frament.ZhiFuBaoCashFragment;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.view.IUserInformationView;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Route(path = "/user/UserInformation")
public class UserInformationActivity extends BaseMVPActivity implements IUserInformationView {
    private static final String TAG = "UserInformationActivity";
    private UserInformationPresenter presenter;

    @Autowired
    public int tag;
    @Autowired
    public float money;
    @Autowired
    public int count;

    Context context;
    CheckUpLoadDialog premissdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        context = this;
        registerMessageReceiver();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_information;
    }

    @Override
    protected void initView() {

    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new UserInformationPresenter(this);
    }

    @Override
    protected void initData() {
        FragmentManager fm = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (tag) {
            case 1:

                transaction.replace(R.id.frag_layout, new UserListInforFragment(tag));
                break;
            case 2:
                /**收入*/
                transaction.replace(R.id.frag_layout, new UserListInforFragment(tag));
                break;
            case 3:
                /**v币*/
                transaction.replace(R.id.frag_layout, new UserListInforFragment(tag));
                break;
            case 4:
                /**V级*/
                transaction.replace(R.id.frag_layout, new UserListInforFragment(tag));
                break;
            case 5:

                transaction.replace(R.id.frag_layout, new ZhiFuBaoCashFragment(money));
                break;
            case 6:

                transaction.replace(R.id.frag_layout, new LuckValueFragment(count));
                break;
            case 7:

                transaction.replace(R.id.frag_layout, new UserListInforFragment(tag));
                break;
            case 8:

                transaction.replace(R.id.frag_layout, new UserListInforFragment(tag));
                break;
            case 9:
                /**消息*/
                transaction.replace(R.id.frag_layout, new UserListInforFragment(tag));
                break;
            case 10:

                transaction.replace(R.id.frag_layout, new UserListInforFragment(tag));
                break;
            case 11:

                transaction.replace(R.id.frag_layout, new UserListInforFragment(tag));
                break;
            case 12:

                transaction.replace(R.id.frag_layout, new UserListInforFragment(tag));
                break;
            case 13:
                /**提供账号列表*/
                transaction.replace(R.id.frag_layout, new UserListInforFragment(tag));
                break;
            case 14:
                /**显示圈子列表*/
                transaction.replace(R.id.frag_layout, CircleFragment.newInstance());
                break;
            default:
                break;
        }

        transaction.commit();// 事务提交
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
        window1 = new JpushPopupwindow(UserInformationActivity.this, ALERT, content);
        window1.showAtLocation(findViewById(R.id.user_information),
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
}
