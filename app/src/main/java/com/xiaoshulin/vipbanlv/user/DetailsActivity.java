package com.xiaoshulin.vipbanlv.user;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.user.presenter.DetailsPresenter;
import com.xiaoshulin.vipbanlv.user.view.IDetailsView;
import com.xiaoshulin.vipbanlv.utils.TitleBar;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;

/**
 * 各种简单的详情页面展示
 */
@Route(path = "/user/DetailsActivity")
public class DetailsActivity extends BaseMVPActivity implements IDetailsView, View.OnClickListener {
    private static final String TAG = "DetailsActivity";
    private TextView tv_message;
    private TitleBar titleBar;
    private Button btn;

    private DetailsPresenter presenter;


    @Autowired
    public int tag;

    @Autowired
    public String message;

    @Autowired
    public String titleinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    protected void initView() {
        titleBar = findViewById(R.id.titlebar);
        tv_message = findViewById(R.id.tv_message);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);


        tv_message.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                copyText(tv_message.getText().toString());
                return false;
            }
        });
    }


    private void copyText(String text) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", text);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        ToastUtil.showToast("已复制到粘贴板");

    }

    @Override
    public BasePresenter initPresenter() {
        presenter = new DetailsPresenter(this);
        return presenter;
    }

    @Override
    protected void initData() {
        switch (tag) {
            case 0:
                /*收入*/
                titleBar.setCenterText("总收入简介");
                tv_message.setText(message);
                btn.setText("提建议有惊喜");
                break;
            case 1:
                /*V币*/
                titleBar.setCenterText("V币简介");
                tv_message.setText(message);
                btn.setText("每一步我都见证");
                break;
            case 2:
                /*V级*/
                titleBar.setCenterText("V级简介");
                tv_message.setText(message);
                btn.setText("提建议有惊喜");
                break;
            case 3:
                /*消息详情*/
                titleBar.setCenterText("消息");
                tv_message.setText(message);
                btn.setText("提建议得奖励");
                break;
            case 4:
                titleBar.setCenterText(titleinfo);
                tv_message.setText(message);
                btn.setText("提建议得奖励");
                break;
            default:
                break;
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
            case R.id.btn:
                jumpAlipay(ToastUtil.zhifubao());
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
}
