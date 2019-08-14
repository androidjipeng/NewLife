package com.xiaoshulin.vipbanlv.fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.activity.OrderListActivity;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.UserBean;
import com.xiaoshulin.vipbanlv.presenter.UserPresenter;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.xiaoshulin.vipbanlv.view.IUserView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends BaseMVPFragment implements IUserView, View.OnClickListener {
    private static final String TAG = "UserFragment";
    private UserPresenter presenter;

    TextView tv1, tv2, tv3, tv4, tv5, tv6;

    LinearLayout linLayout2, linLayout3, linLayout4, linLayout5, linLayout6, linLayout7, linLayout8, linLayout9, linLayout10, linLayout11, linLayout12;

    Button btn_code;

    private float money;
    UserBean bean;

    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getUserInfo();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        btn_code = mRootView.findViewById(R.id.btn_code);
        btn_code.setOnClickListener(this);
        tv1 = mRootView.findViewById(R.id.tv1);
        tv2 = mRootView.findViewById(R.id.tv2);
        tv3 = mRootView.findViewById(R.id.tv3);
        tv4 = mRootView.findViewById(R.id.tv4);
        tv5 = mRootView.findViewById(R.id.tv5);
        tv6 = mRootView.findViewById(R.id.tv6);


        linLayout2 = mRootView.findViewById(R.id.linLayout2);
        linLayout3 = mRootView.findViewById(R.id.linLayout3);
        linLayout4 = mRootView.findViewById(R.id.linLayout4);
        linLayout5 = mRootView.findViewById(R.id.linLayout5);
        linLayout6 = mRootView.findViewById(R.id.linLayout6);
        linLayout7 = mRootView.findViewById(R.id.linLayout7);
        linLayout8 = mRootView.findViewById(R.id.linLayout8);
        linLayout9 = mRootView.findViewById(R.id.linLayout9);
        linLayout10 = mRootView.findViewById(R.id.linLayout10);
        linLayout11 = mRootView.findViewById(R.id.linLayout11);
        linLayout12 = mRootView.findViewById(R.id.linLayout12);

        linLayout2.setOnClickListener(this);
        linLayout3.setOnClickListener(this);
        linLayout4.setOnClickListener(this);
        linLayout5.setOnClickListener(this);
        linLayout6.setOnClickListener(this);
        linLayout7.setOnClickListener(this);
        linLayout8.setOnClickListener(this);
        linLayout9.setOnClickListener(this);
        linLayout10.setOnClickListener(this);
        linLayout11.setOnClickListener(this);
        linLayout12.setOnClickListener(this);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new UserPresenter(this);
    }

    @Override
    public void UserData(UserBean bean) {
        this.bean = bean;
        money = bean.getMoney();
        tv1.setText(bean.getUsername());
        tv2.setText(bean.getIncome() + "元");
        tv3.setText(bean.getVmoney() + "V币");
        tv4.setText(bean.getV() + "V");
        String str = "可提现金额:<font color='#FF0000'>" + bean.getMoney() + "元" + "</font>";
        tv5.setText(Html.fromHtml(str));
        String cont=bean.getUsercount()+"";
        String mess = "第 <font color='#FF0000'>" + SharePreferenceUtil.getinstance().getStringUId() + "</font>位  总用户数量"+Utils.formatNum(cont,false) +"+";
        tv6.setText(Html.fromHtml(mess));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.linLayout2:
                Log.e(TAG, "onClick: linLayout2");
                ARouter.getInstance()
                        .build("/user/UserInformation")
                        .withInt("tag", 2)
                        .navigation();
                break;
            case R.id.linLayout3:
                Log.e(TAG, "onClick: linLayout3");
                ARouter.getInstance()
                        .build("/user/UserInformation")
                        .withInt("tag", 3)
                        .navigation();
                break;
            case R.id.linLayout4:
                Log.e(TAG, "onClick: linLayout4");
                ARouter.getInstance()
                        .build("/user/UserInformation")
                        .withInt("tag", 4)
                        .navigation();
                break;
            case R.id.linLayout5:
                Log.e(TAG, "onClick: linLayout5");
                ARouter.getInstance()
                        .build("/user/UserInformation")
                        .withInt("tag", 5)
                        .withFloat("money", money)
                        .navigation();
                break;
            case R.id.linLayout6:
                /**试用账号管理*/
                Log.e(TAG, "onClick: linLayout6");
                ARouter.getInstance()
                        .build("/user/UserInformation")
                        .withInt("tag", 6)
                        .withInt("count", bean.getUsercount())
                        .navigation();
                break;
            case R.id.linLayout7:
                Log.e(TAG, "onClick: linLayout7");
                ARouter.getInstance()
                        .build("/user/UserInformation")
                        .withInt("tag", 7)
                        .navigation();
                break;
            case R.id.linLayout8:
                Intent intent = new Intent(getActivity(), OrderListActivity.class);
                startActivity(intent);
//                ARouter.getInstance()
//                        .build("/user/UserInformation")
//                        .withInt("tag",8)
//                        .navigation();
                break;
            case R.id.linLayout9:
                /**消息*/
//                ToastUtil.showToast("消息待开发...");
                ARouter.getInstance()
                        .build("/user/UserInformation")
                        .withInt("tag", 9)
                        .navigation();
                break;
            case R.id.linLayout10:
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
                break;
            case R.id.linLayout11:
                String url = "http://www.vipbanlv.com/v2_test/#!/check-in";
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url", url)
                        .navigation();
                break;
            case R.id.linLayout12:
//                ToastUtil.showToast("设置待开发...");
                Uri uri = Uri.parse("http://www.vipbanlv.com/apk/vipbanlv.apk");
                Intent checkintent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(checkintent);
                break;
            case R.id.btn_code:
                  String urlmoney="https://mp.weixin.qq.com/s/21jo3IOk-pgHPc3LldKYTQ";
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url", urlmoney)
                        .navigation();

                break;
            default:
                break;

        }
    }

}
