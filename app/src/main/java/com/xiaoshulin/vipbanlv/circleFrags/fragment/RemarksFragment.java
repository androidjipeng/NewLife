package com.xiaoshulin.vipbanlv.circleFrags.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.EventBusMessage;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.circleFrags.presenter.CricleRemarksPresenter;
import com.xiaoshulin.vipbanlv.circleFrags.view.ICricleRemarksView;
import com.xiaoshulin.vipbanlv.utils.Constants;
import com.xiaoshulin.vipbanlv.utils.MyTextView;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;

/**
 * 圈子备注
 */
public class RemarksFragment extends BaseMVPFragment implements ICricleRemarksView, View.OnClickListener {

    private String cricleId,produceid;

    private CricleRemarksPresenter presenter;

    private Button btn_left, btn_right;
    private MyTextView remark_information;

    public RemarksFragment() {

    }

    @SuppressLint("ValidFragment")
    public RemarksFragment(String cricleId,String produceid) {
        this.cricleId = cricleId;
        this.produceid=produceid;
    }


    public static RemarksFragment newInstance(String cricleId,String produceid) {
        RemarksFragment fragment = new RemarksFragment(cricleId,produceid);
        return fragment;
    }


    @Override
    protected void initData() {

        presenter.CricleRemarkInformation(cricleId);
    }

    @Override
    protected void initView() {
        btn_left = mRootView.findViewById(R.id.btn_left);
        btn_right = mRootView.findViewById(R.id.btn_right);

        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
        remark_information = mRootView.findViewById(R.id.remark_information);

        remark_information.setOnUrlClickLitener(new MyTextView.OnUrlClickLitener() {
            @Override
            public void onUrlClick(String url) {
                ParsingTools tools=new ParsingTools();
                tools.SecondParseTool(getActivity(),url);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cricle_remarks_layout;
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new CricleRemarksPresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                /**进行客服咨询*/
                SharePreferenceUtil util = SharePreferenceUtil.getinstance();
                String stringUId = util.getStringUId();
//                DoCurstomer("用户："+stringUId,"temporary16",cricleId);
                presenter.DoCurstomer(getContext(),"用户："+stringUId,"temporary16",remark_information.getText().toString(),cricleId,produceid);
                break;
            case R.id.btn_right:
                /**官方客服*/
                ARouter.getInstance()
                        .build("/activity/OrderInformationActivity")
                        .withString("Name", "小树林客服")
                        .withString("Dayend", "")
                        .withString("Wechat", "")
                        .withString("Orderid", "")
                        .withString("Password", "")
                        .withString("pushuid", "")
                        .withString("pushwechat", "")
                        .withInt("tag", 0)
                        .navigation();
                break;
            default:
                break;
        }
    }

    @Override
    public void sendSuccess() {
        /**发送信息成功*/
        ARouter.getInstance()
                .build("/activity/ChatMessageActivity")
                .withString("titleName", "客服小哥")
                .withString("messageSectionid", "1374")
                .navigation();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().post(new EventBusMessage(4));
        super.onDestroy();
    }

    @Override
    public void getRemarksInformation(String message) {
        /**备注信息*/
        String url="";
        try {
            url= URLDecoder.decode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
          remark_information.handleText(url);
    }

//    private void DoCurstomer(String name, String avatar, String circleuid) {
//        JPushBean bean = new JPushBean();
//        String mess = "我对您的商品有一些疑惑，想咨询一下。\n" +
//                "\n" + "\uD83D\uDC8B" +
//                "长按复制这条消息即可进入圈子详情页。\n" +
//                "圈子ID：" + "\uD83D\uDC8B" + circleuid + "*8*" + SharePreferenceUtil.getinstance().getStringUId() + "\uD83D\uDC8B" + "\n" +
//                "请回复问题对应的数字：\n" +
//                "1：购买的圈子商品无法查看订单。\n" +
//                "2：登录问题。\n" +
//                "3：商品使用问题\n" +
//                "4：圈子有违法、违纪内容！\n" +
//                "5：我有一些好的建议。\n" +
//                "\n" + "\uD83D\uDC8B" +
//                "在《小树林》APP平台的圈子信息：\n" +
//                remark_information.getText().toString() +
//                "\n\n" +
////                "圈子剩余担保期：365天\n" +
//                "\uD83D\uDC8B" +
//                "发布人ID：" + circleuid + "\n" + "\uD83D\uDC8B" +
//                "微信客服：xiaoshulinapp";
//        bean.setContent(mess);
//        JSONObject customfield = new JSONObject();
//        try {
//            customfield.put("nickName", name);
//            customfield.put("type", "0");
//            customfield.put("icon", avatar);
//            customfield.put("messageSection", circleuid);//iOrderInformView.getpushuid()
//            customfield.put("time", Utils.getTime1());
//            customfield.put("chatId", Utils.getTime2());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        bean.setCustomfield(customfield.toString());
//        bean.setPushtype("14");
//        /**保存数据*/
//        Constants.saveData(getContext(), bean);
//        presenter.sendInformation(mess, circleuid, avatar);
//    }




}
