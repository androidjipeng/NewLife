package com.xiaoshulin.vipbanlv.circleFrags.fragment;


import android.annotation.SuppressLint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.EventBusMessage;
import com.xiaoshulin.vipbanlv.circleFrags.presenter.CricleRemarksPresenter;
import com.xiaoshulin.vipbanlv.circleFrags.view.ICricleRemarksView;
import com.xiaoshulin.vipbanlv.utils.MyTextView;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedList;

/**
 * 从别的地方解析出来的圈子备注
 */
public class RemarksFragment1 extends BaseMVPFragment implements ICricleRemarksView, View.OnClickListener {

    private String cricleId,produceid;
    private CricleRemarksPresenter presenter;

    private Button btn_left, btn_right;
    private MyTextView remark_information;

    public RemarksFragment1() {

    }

    @SuppressLint("ValidFragment")
    public RemarksFragment1(String cricleId, String produceid) {
        this.cricleId = cricleId;
        this.produceid=produceid;
    }



    public static RemarksFragment1 newInstance(String cricleId, String produceid) {
        RemarksFragment1 fragment = new RemarksFragment1(cricleId,produceid);
        return fragment;
    }


    @Override
    protected void initData() {

        presenter.CricleRemarkInformation(cricleId,produceid);
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
            public void onUrlClick( String url) {
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
                presenter.DoCurstomer(getContext(),"用户："+stringUId,"temporary16","1374",remark_information.getText().toString(),cricleId,produceid);
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

}
