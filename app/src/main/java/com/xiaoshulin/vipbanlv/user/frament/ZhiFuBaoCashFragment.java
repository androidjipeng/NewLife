package com.xiaoshulin.vipbanlv.user.frament;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.PremissionsDialog;
import com.xiaoshulin.vipbanlv.user.presenter.ZhiFuBaoPresenter;
import com.xiaoshulin.vipbanlv.user.view.IZhuFuBaoView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhiFuBaoCashFragment extends BaseMVPFragment implements IZhuFuBaoView, View.OnClickListener {

    private ZhiFuBaoPresenter presenter;

    private float money;

    private TextView money_value;

    private EditText editText;

    private Button cashMoney,btn_custom;
    private PremissionsDialog dialog;

    private CheckUpLoadDialog checkUpLoadDialog;

    public ZhiFuBaoCashFragment() {

    }

    @SuppressLint("ValidFragment")
    public ZhiFuBaoCashFragment(float money) {
        this.money = money;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        cashMoney = mRootView.findViewById(R.id.cashMoney);
        cashMoney.setOnClickListener(this);

        btn_custom=mRootView.findViewById(R.id.btn_custom);
        btn_custom.setOnClickListener(this);

        editText = mRootView.findViewById(R.id.account);
        money_value = mRootView.findViewById(R.id.money_value);
        money_value.setText(money + "元");

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zhi_fu_bao_cash;
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new ZhiFuBaoPresenter(this);
    }


    @Override
    public String getzhifubaoAccount() {
        return editText.getText().toString();
    }

    @Override
    public void zhifuBaoSuccess(int returncode) {

        if (returncode == 0) {

            dialog = new PremissionsDialog(getActivity(), "提现申请已成功提交，提现金额将在一到两个工作日到您的提现支付宝账号", new Listener());

        } else {
            dialog = new PremissionsDialog(getActivity(), "提现申请失败，请稍后重试", new Listener());
        }

        dialog.show();

    }


    class Listener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            dialog.dismiss();
            getActivity().finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cashMoney:
                if (money > 10) {
                  checkUpLoadDialog = new CheckUpLoadDialog(getContext(), "温馨提示", "提现支付宝账号：《" + editText.getText().toString() + "》除去1/10服务费实际到账金额《0.00》将在2到5个工作日到账", "重填", "确定", new CashListener());
                  checkUpLoadDialog.show();
                } else {
                    dialog = new PremissionsDialog(getActivity(), "可提现金额要大于等于10元才能提现哦", new CheckListener());
                    dialog.show();
                }
                break;
            case R.id.btn_custom:
                ARouter.getInstance()
                        .build("/activity/OrderInformationActivity")
                        .withString("Name","小树林客服")
                        .withString("Dayend","")
                        .withString("Wechat","")
                        .withString("Orderid","")
                        .withString("Password","")
                        .withString("pushuid","")
                        .withString("pushwechat","")
                        .withInt("tag",0)
                        .navigation();
//                ARouter.getInstance()
//                        .build("/activity/ChatMessageActivity")
//                        .withString("titleName", "客服小哥")
//                        .withString("messageSectionid","1374")
//                        .navigation();
                break;
            default:
                break;
        }
    }

    class CashListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_cancel:
                    checkUpLoadDialog.dismiss();
                    break;
                case R.id.tv_submit:
                    presenter.ZhiFuBaoCase();
                    checkUpLoadDialog.dismiss();
                    break;

            }
        }
    }

    class CheckListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    }
}
