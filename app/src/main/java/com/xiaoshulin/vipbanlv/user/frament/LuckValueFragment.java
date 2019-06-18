package com.xiaoshulin.vipbanlv.user.frament;


import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.user.presenter.LuckValuePresenter;
import com.xiaoshulin.vipbanlv.user.view.ILuckValueView;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class LuckValueFragment extends BaseMVPFragment implements ILuckValueView, View.OnClickListener {

    private TextView content;
    private Button submit_award;

    private LuckValuePresenter presenter;

    int count;

    public LuckValueFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public LuckValueFragment(int count) {
        this.count=count;

    }


    @Override
    protected void initData() {

        String str="恭喜您进入了前十万名种子用户培养计划。\n您目前的用户注册缘分值是：第<font color='#FF0000'>"+ SharePreferenceUtil.getinstance().getStringUId()+"</font>位。总用户：<font color='#FF0000'>"+count+"</font>\n《小树林》APP特别相信缘分，越早的相见就说明我们越有缘分，这也是我们发起前十万名种子用户培养计划的初衷，APP以后推出的各种优惠活动都会优先考虑前十万的种子用户。还会从前十万用户中找到那些给APP提供宝贵建议，愿意和我们一起成长的铁杆用户，让他们加入万人合伙人计划，我们会不定期的给合伙人发放分红，让有付出的人得到应有的回报，也是我们的宗旨之一。";
        content.setText(Html.fromHtml(str));
    }

    @Override
    protected void initView() {
        content = mRootView.findViewById(R.id.content);
        submit_award = mRootView.findViewById(R.id.submit_award);
        submit_award.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_luck_value;
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new LuckValuePresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit_award:
                jumpAlipay(ToastUtil.zhifubao());
                break;
        }
    }

    private void jumpAlipay(String message) {

        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", message);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        //跳转支付宝
        try {
            PackageManager packageManager
                    = getActivity().getApplicationContext().getPackageManager();
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
