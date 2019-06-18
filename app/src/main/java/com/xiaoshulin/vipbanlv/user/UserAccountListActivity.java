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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.activity.ShareWechatActivity;
import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.CostomFieldBean;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.bean.UserAccountListBean;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.jpush.LocalBroadcastManager;
import com.xiaoshulin.vipbanlv.popup.JpushPopupwindow;
import com.xiaoshulin.vipbanlv.user.adapter.UserAccountListAdater;
import com.xiaoshulin.vipbanlv.user.presenter.UserAccountListPresenter;
import com.xiaoshulin.vipbanlv.user.view.IUserAccountListView;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.TitleBar;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 用户中心  账号列表
 * */
@Route(path = "/user/UserAccountListActivity")
public class UserAccountListActivity extends BaseMVPActivity implements IUserAccountListView {
    private static final String TAG = "UserAccountListActivity";
    UserAccountListPresenter presenter;
    private XRecyclerView common_recycle;

    private TitleBar titleBar;

    @Autowired
    public String title;
    @Autowired
    public String pid;

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
        return R.layout.activity_user_account_list;
    }

    @Override
    protected void initView() {
        common_recycle = findViewById(R.id.common_recycle);
        titleBar = findViewById(R.id.titlebar);
        titleBar.setCenterText(title);
//        titleBar.setLeftClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        common_recycle.setLayoutManager(manager);
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new UserAccountListPresenter(this);
    }

    @Override
    protected void initData() {
        presenter.UserAccountList();
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
    public void getAccountListData(UserAccountListBean bean) {
        UserAccountListAdater adater=new UserAccountListAdater(UserAccountListActivity.this,bean.getList());
        common_recycle.setAdapter(adater);
        adater.setOnItemClickLitener(new UserAccountListAdater.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (bean.getList().get(position).getEndtime().equals("已过期")){
                    ARouter.getInstance()
                            .build("/activity/SupplyAccountActivity")
                            .withString("title",title)
                            .withString("aid",bean.getList().get(position).getAid())
                            .withString("pid",pid)
                            .withString("tag","1")
                            .navigation();
                }else {
                    ARouter.getInstance()
                            .build("/user/AccountInformationActivity")
                            .withString("title",title)
                            .withString("aid",bean.getList().get(position).getAid())
                            .navigation();
                }
            }
        });
    }

    @Override
    public String getpid() {
        return pid;
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
                    }
                }
            } catch (Exception e) {
            }
        }
    }



    @SuppressLint("NewApi")
    private void setCostomMsg(String ALERT,String content) {
        window1 = new JpushPopupwindow(UserAccountListActivity.this,ALERT,content);
        window1.showAtLocation(findViewById(R.id.ll_accountlist),
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
