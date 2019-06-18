package com.xiaoshulin.vipbanlv.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.MainActivity;
import com.xiaoshulin.vipbanlv.SpalshActivity;
import com.xiaoshulin.vipbanlv.WebActivity;
import com.xiaoshulin.vipbanlv.adapter.OrderListAdapter;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.CostomFieldBean;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.bean.LocalDataBean;
import com.xiaoshulin.vipbanlv.bean.OrderListBean;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.jpush.LocalBroadcastManager;
import com.xiaoshulin.vipbanlv.popup.JpushPopupwindow;
import com.xiaoshulin.vipbanlv.presenter.OrderLIstPresenter;
import com.xiaoshulin.vipbanlv.utils.DataBaseUtils;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.view.OrderListVIew;

import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderListActivity extends BaseMVPActivity implements OrderListVIew, View.OnClickListener {
    private static final String TAG = "OrderListActivity";
    RelativeLayout relativeLayout;
    TextView btn_web;
    private OrderLIstPresenter presenter;
    private RecyclerView Order_recycle;
    Context context;
    CheckUpLoadDialog premissdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        registerMessageReceiver();
    }

    /**
     * 设置标签与别名
     */
    private void setTagAndAlias() {
        /**
         *这里设置了别名，在这里获取的用户登录的信息
         *并且此时已经获取了用户的userId,然后就可以用用户的userId来设置别名了
         **/
        //false状态为未设置标签与别名成功
        //if (UserUtils.getTagAlias(getHoldingActivity()) == false) {
        Set<String> tags = new HashSet<String>();
        //这里可以设置你要推送的人，一般是用户uid 不为空在设置进去 可同时添加多个
        SharePreferenceUtil util = SharePreferenceUtil.getinstance();

        if (!TextUtils.isEmpty(util.getStringUId())) {
            tags.add(util.getStringUId());//设置tag
        }
        //上下文、别名【Sting行】、标签【Set型】、回调
        JPushInterface.setAliasAndTags(context, util.getStringUId(), tags,
                mAliasCallback);
        // }
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    //UserUtils.saveTagAlias(getHoldingActivity(), true);
                    logs = "Set tag and alias success极光推送别名设置成功";
                    Log.e("TAG", logs);
                    break;
                case 6002:
                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
                    Log.e("TAG", logs);
                    break;
                default:
                    logs = "极光推送设置失败，Failed with errorCode = " + code;
                    Log.e("TAG", logs);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initView() {
        relativeLayout = findViewById(R.id.rela);
        btn_web = findViewById(R.id.btnweb);
        btn_web.setOnClickListener(this);

        Order_recycle = findViewById(R.id.Order_recycle);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        Order_recycle.setLayoutManager(manager);

    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new OrderLIstPresenter(this);
    }

    @Override
    protected void initData() {
        presenter.orderListData();
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
    OrderListAdapter adapter=null;
    @Override
    public void getOrderListData(OrderListBean bean) {
        if (adapter==null)
        {
            adapter = new OrderListAdapter(context, bean.getList());
            Order_recycle.setAdapter(adapter);
        }else {
            adapter.setList(bean.getList());
            adapter.notifyDataSetChanged();
        }

        if (bean.getList().size() == 0) {
            relativeLayout.setVisibility(View.VISIBLE);
        }else {
            relativeLayout.setVisibility(View.GONE);
        }

        verifyStoragePermissions(OrderListActivity.this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnweb:
                String url="http://www.vipbanlv.com";
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url", url)
                        .navigation();
                break;
            default:
                break;
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
    private void setCostomMsg(String ALERT,String content) {
        window1 = new JpushPopupwindow(OrderListActivity.this,ALERT,content);
        window1.showAtLocation(findViewById(R.id.conl),
                Gravity.CENTER | Gravity.TOP,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()));
        window1.showAsDropDown(findViewById(R.id.conl),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -20, context.getResources().getDisplayMetrics()),
                Gravity.CENTER | Gravity.TOP);
        window1.update();

    }
    /**   end*/



    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    PremissionsDialog premissionsDialog;

    class premissDialogListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_ok:
                    premissionsDialog.dismiss();
                    ActivityCompat.requestPermissions(OrderListActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
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
                premissionsDialog=new PremissionsDialog(activity,"为了防止您的老数据丢失，请同意小树林APP的存储权限哦",new premissDialogListener());
                premissionsDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存文件到本机存储
     *
     * @param toSaveString
     */
    public void savefile(String toSaveString,String uidtoken) {

        Log.e(TAG, "savefile:       ---------------> toSaveString:"+toSaveString);
        int num = Integer.parseInt(toSaveString) + 214;
        Log.e(TAG, "savefile:       ---------------> num:"+num);
        int num1 = num * 3;
        Log.e(TAG, "savefile:       ---------------> num1:"+num1);
        String a = "ab3478re109qw" + num1 + "scd66487ab3t";
        Log.e(TAG, "savefile:       ---------------> a:"+a);
        a=a+"*"+uidtoken;
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


    /**
     * 读取本机存储文件内容
     *
     * @return 文件内容
     */
    public LocalDataBean readfile() {

        File file = new File(Environment.getExternalStorageDirectory(), "/android1/weixinxslapi.txt");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        InputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {

            in = new FileInputStream(file.getAbsolutePath());//文件名
            InputStreamReader input = new InputStreamReader(in, "UTF-8");
            reader = new BufferedReader(input);
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        LocalDataBean bean=new LocalDataBean();
        String a = content.toString();
        if (TextUtils.isEmpty(a))
        {
            return bean;
        }
        String[] split = a.split("\\*");
        String A=split[0];
        String B=split[1];
        Log.e(TAG, "readfile:   加密后取出来的数据------------->a:"+a );
        String b = A.substring(13, A.length() - 12);
        Log.e(TAG, "readfile:   ------------->b:"+b );
        int c = Integer.parseInt(b) / 3;
        Log.e(TAG, "readfile:   ------------->c:"+c );
        String uid = String.valueOf(c - 214);
        Log.e(TAG, "readfile:   ------------->uid:"+uid );

        bean.setUid(uid);
        bean.setUidtoken(B);
        return bean;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限被用户同意，可以去放肆了。
                Log.e("jp", "onRequestPermissionsResult:  权限被用户同意，可以去放肆了");
                LocalDataBean localDataBean = readfile();
                if (adapter.getList().size()==0&&(!TextUtils.isEmpty(localDataBean.getUid())))
                {
                        SharePreferenceUtil.getinstance().setStringUID(localDataBean.getUid());
                        SharePreferenceUtil.getinstance().setStringUIdToken(localDataBean.getUidtoken());
                        setTagAndAlias();
                        presenter.orderListData();

                }else {
                    String stringUId = SharePreferenceUtil.getinstance().getStringUId();
                    String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
                    savefile(stringUId,stringUIdToken);
                }

            } else {

                // 权限被用户拒绝了，洗洗睡吧。
                Log.e("jp", "onRequestPermissionsResult:  权限被用户拒绝了，洗洗睡吧");

            }
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
            String url = "https://ds.alipay.com/?from=mobileweb";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }
}
