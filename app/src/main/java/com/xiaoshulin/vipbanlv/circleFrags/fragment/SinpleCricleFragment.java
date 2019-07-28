package com.xiaoshulin.vipbanlv.circleFrags.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.alipay.PayResult;
import com.xiaoshulin.vipbanlv.alipay.SignUtils;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.CheckVisionBean;
import com.xiaoshulin.vipbanlv.bean.CircleAdvertisementBean;
import com.xiaoshulin.vipbanlv.bean.CricleBean;
import com.xiaoshulin.vipbanlv.bean.CricleBuyPro;
import com.xiaoshulin.vipbanlv.bean.CricleBuyToInterial;
import com.xiaoshulin.vipbanlv.bean.EventBusMessage;
import com.xiaoshulin.vipbanlv.bean.JPushBean;
import com.xiaoshulin.vipbanlv.bean.SupportBean;
import com.xiaoshulin.vipbanlv.bean.singleCircleBean;
import com.xiaoshulin.vipbanlv.circleFrags.adapter.CircleAdapter1;
import com.xiaoshulin.vipbanlv.circleFrags.presenter.SinpleCirclePresenter;
import com.xiaoshulin.vipbanlv.circleFrags.view.ISinpleCircleView;
import com.xiaoshulin.vipbanlv.dialog.CheckUpLoadDialog;
import com.xiaoshulin.vipbanlv.dialog.WarningDialog;
import com.xiaoshulin.vipbanlv.utils.Constants;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SinpleCricleFragment extends BaseMVPFragment implements ISinpleCircleView {

    private SinpleCirclePresenter presenter;

    private String cricleid;

    private XRecyclerView content_recycle;
    List<CricleBean.DataBean> list = new ArrayList<>();
    private CircleAdapter1 adapter;
    private ImageView imageView, img;
    private TextView tv_uid;
    private String generalizeurl;
    private int page = 1;
    private String stringUId;
    private BottomSheetDialog bottomSheet;
    private String Circleid;

    private String days;
    private String price;
    String messContent, cricleids;
    private String pusherid;
    private boolean isprice = true;
    singleCircleBean.CircleBean circle;
    public SinpleCricleFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public SinpleCricleFragment(String cricleid) {
        this.cricleid = cricleid;
    }

    public static SinpleCricleFragment newInstance(String cricleid) {
        SinpleCricleFragment fragment = new SinpleCricleFragment(cricleid);
        return fragment;
    }

    @Override
    protected void initData() {
        presenter.getCircleAdvertisement();
        presenter.getSinpleCircleData(cricleid);
    }

    @Override
    protected void initView() {
        content_recycle = mRootView.findViewById(R.id.single_content_recycle);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        content_recycle.setLayoutManager(manager);

        adapter = new CircleAdapter1(getContext(), list, 1);
        content_recycle.setAdapter(adapter);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.circle_top_tip_layout, null);
        imageView = view.findViewById(R.id.img_adv);

        img = view.findViewById(R.id.img);
        String myIcon = SharePreferenceUtil.getinstance().getMyIcon();
        try {
            InputStream is = getContext().getResources().getAssets().open(myIcon + ".png");
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            img.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build("/activity/WebUserActivity")
                        .withInt("tag", 1)
                        .withString("cricleid", circle.getCircleuid())
                        .withString("produceId", circle.getCircleid())
                        .navigation();
            }
        });


        tv_uid = view.findViewById(R.id.tv_uid);
        String stringUId = SharePreferenceUtil.getinstance().getStringUId();
        tv_uid.setText("缘分值：" + stringUId);
        tv_uid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build("/activity/WebUserActivity")
                        .withInt("tag", 1)
                        .withString("cricleid", circle.getCircleuid())
                        .withString("produceId", circle.getCircleid())
                        .navigation();
            }
        });

        content_recycle.addHeaderView(view);

        content_recycle.setPullRefreshEnabled(false);
        content_recycle.setLoadingMoreEnabled(false);

        setAdapterListener();

    }


    private void DoCurstomer(String name, String avatar, String circleuid) {
        JPushBean bean = new JPushBean();
        String mess = "我对您的商品有一些疑惑，想咨询一下。\n" +
                "\n" + "\uD83D\uDC8B" +
                "长按复制这条消息即可进入圈子详情页。\n" +
                "圈子ID：" + "\uD83D\uDC8B" + cricleids + "*8*1374" + "\uD83D\uDC8B" + "\n" +
                "请回复问题对应的数字：\n" +
                "1：购买的圈子商品无法查看订单。\n" +
                "2：登录问题。\n" +
                "3：商品使用问题\n" +
                "4：圈子有违法、违纪内容！\n" +
                "5：我有一些好的建议。\n" +
                "\n" + "\uD83D\uDC8B" +
                "在《小树林》APP平台的圈子信息：\n" +
                messContent +
                "\n\n" +
                "圈子剩余担保期：365天\n" + "\uD83D\uDC8B" +
                "发布人ID："+circleuid+"\n" + "\uD83D\uDC8B" +
                "微信客服：xiaoshulinapp";
        bean.setContent(mess);
        JSONObject customfield = new JSONObject();
        try {
            customfield.put("nickName", name);
            customfield.put("type", "0");
            customfield.put("icon", avatar);
            customfield.put("messageSection", circleuid);//iOrderInformView.getpushuid()
            customfield.put("time", Utils.getTime1());
            customfield.put("chatId", Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bean.setCustomfield(customfield.toString());
        bean.setPushtype("14");
        /**保存数据*/
        Constants.saveData(getContext(), bean);
        presenter.sendInformation(mess, circleuid, avatar);
    }

    private void showBottomSheetDialog() {
        presenter.V_money();

    }

    private void setAdapterListener() {
        adapter.setOnItemClickLitener(new CircleAdapter1.OnItemClickLitener() {
            @Override
            public void onItemAllClick(View view, int position, int tag) {
                String circleid = list.get(position).getCircleid();
                //tag=0为收藏
                switch (tag) {
                    case 0:
                        String iscollect = list.get(position).getIscollect();
                        if (iscollect.equals("0")) {
                            presenter.IsCollect(circleid, "1");
                        } else {
                            presenter.IsCollect(circleid, "0");

                        }

                        break;
                    case 1:
                        /**立即抢购*/
//                         presenter.BuyPro(list.get(position).getCircleid(),"1");
                        pusherid = list.get(position).getCircleuid();
                        Circleid = list.get(position).getCircleid();
                        days = list.get(position).getAssuredate();
                        price = list.get(position).getPrice();
                        showBottomSheetDialog();
                        break;
                    case 2:
                        /**客服咨询*/
                        messContent = list.get(position).getContent();
                        cricleids = list.get(position).getCircleid();
                        DoCurstomer(list.get(position).getName(), list.get(position).getAvatar(), list.get(position).getCircleuid());

//                        jumpAlipay(ToastUtil.zhifubao());
                        break;
                    case 3:
                        //分享
                        String shareCircle = "在《小树林APP》几毛钱可以购买各种物品，还可以分享给自己的家人朋友，然后大家都可以免费享受哦。\n"
                                + "\uD83D\uDC8B" + "长按复制这条信息然后打开《小树林APP》就可以查看超级好用的商品详情了。\n"
                                + "小树林商品ID：" + "\uD83D\uDC8B" + circleid + "*8*" + stringUId + "\n" + "\uD83D\uDC8B"
                                + "\n小树林APP各大视频网站会员一个月只要几块钱。\n"
                                + "\uD83D\uDC8B" + "点击链接即可试用http://www.vipbanlv.com/www_v1\n\n"
                                + "\uD83D\uDC8B" + "安卓品牌手机可以点击这个链接下载app：http://dwz.cn/86EfALIU\n\n"
                                + "\uD83D\uDC8B" + "苹果手机可以点击这个链接下载app：http://dwz.cn/lqrDxatM";
                        ARouter.getInstance()
                                .build("/activity/ShareWechat")
                                .withString("copystring", shareCircle)
                                .navigation();

                        break;
                    case 4:
                        //删除
                        presenter.delectCircle(list.get(position).getCircleid());
                        break;
                    case 5:
                        //编辑
                        break;
                    case 6:
                        //置顶
                        break;
                    case 7:
                        //欣赏
                        presenter.getSupportMoney(list.get(position).getCircleid(),list.get(position).getCircleuid());
                        break;
                    case 8:

                        ARouter.getInstance()
                                .build("/activity/WebUserActivity")
                                .withInt("tag", 1)
                                .withString("cricleid", list.get(position).getCircleuid())
                                .withString("produceId", list.get(position).getCircleid())
                                .navigation();
                        break;
                    case 9:
                        /**复制到粘贴板中*/
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                        ClipData mClipData = ClipData.newPlainText("Label", list.get(position).getContent());
                        // 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
                        ToastUtil.showToast("已复制到粘贴板");
                        break;
                    case 10:
                        /**投诉*/
                        String content = list.get(position).getContent();
                        String newContent="";
                        if (content.length()>88){
                            newContent=content.substring(0,88);
                        }else {
                            newContent=content;
                        }
                        cricleids=list.get(position).getCircleid();
                        String mess="圈子商品使用中遇到了问题。\n" +
                                "\n" + "\uD83D\uDC8B" +
                                " 长按复制这条消息即可进入圈子详情页。\n" +
                                "圈子ID:" + "\uD83D\uDC8B" +cricleids+"*8*"+SharePreferenceUtil.getinstance().getStringUId()+"\uD83D\uDC8B" +"\n" +
                                "请回复问题对应的数字：\n" +
                                "1：购买的圈子商品无法查看订单。\n" +
                                "2：登录问题。\n" +
                                "3：商品使用问题\n" +
                                "4：圈子有违法、违纪内容！\n" +
                                "5：我有一些好的建议。\n" +
                                "\n" + "\uD83D\uDC8B" +
                                " 在《小树林》APP平台购买的圈子信息：\n" +newContent+
                                "\n" +
                                "\n" +
                                "圈子剩余担保期：365天\n" + "\uD83D\uDC8B" +
                                " 发布人ID："+list.get(position).getCircleuid()+"\n" + "\uD83D\uDC8B" +
                                " 微信客服：xiaoshulinapp";


                        complain(list.get(position).getName(), list.get(position).getAvatar(),"1374",mess);
//                        ARouter.getInstance()
//                                .build("/activity/OrderInformationActivity")
//                                .withString("Name", "小树林客服")
//                                .withString("Dayend", "")
//                                .withString("Wechat", "")
//                                .withString("Orderid", "")
//                                .withString("Password", "")
//                                .withString("pushuid", "")
//                                .withString("pushwechat", "")
//                                .withInt("tag", 0)
//                                .navigation();
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void complain(String name, String avatar, String circleuid,String mess) {
        JPushBean bean = new JPushBean();
        bean.setContent(mess);
        JSONObject customfield = new JSONObject();
        try {
            customfield.put("nickName", name);
            customfield.put("type", "0");
            customfield.put("icon", avatar);
            customfield.put("messageSection", circleuid);//iOrderInformView.getpushuid()
            customfield.put("time", Utils.getTime1());
            customfield.put("chatId", Utils.getTime2());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bean.setCustomfield(customfield.toString());
        bean.setPushtype("14");
        /**保存数据*/
        Constants.saveData(getContext(), bean);
        presenter.sendInformation(mess, circleuid, avatar);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sinple_cricle;
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new SinpleCirclePresenter(this);
    }

    @Override
    public void CircleListData(singleCircleBean bean) {
        /*circleid : 415
         * circleuid : 1374
         * avatar : temporary11
         * name : 用户ID：1374
         * content : 腾讯大王卡#免费办理，还可以自己选择手机号码，办理就送70元话费#联通卡流量无限卡4g上网卡流量卡，腾讯有关所有app免流量使用#未满十六周岁不可办理#，如需要办理请点击下方超链，进入网页后先选择自己需要的王卡类型，然后点击免费申请填写相关信息就可以免费办理了，如有什么疑问可以点击客服咨询
         * imgs : ["https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV80OWYzYmU1OS1hZmJiLTRhNDQtOGE1NS1iZDc4MDhiYmZiZjc=","https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV8zZmQ3ZjlkOC1iZmU0LTRjNTMtOTE0Yi05MTAwOThlZmUyMjY=","https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV9lOTEwYWQ5Yi1mYmU4LTQ5MDUtYTBkMC1lMTYyYmY3NGU3M2I=","https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV8yMWRlM2VjMi1hZDg2LTRjNDktOTBjZi1hZGQxZmUzMmM2MzE=","https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV83MjNmOTZmMS01MTVmLTQwMDgtOTBjYy0wNWZkNzhlNjQ3NGY=","https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV9jZDRhYWUxYy05Y2JmLTQ1OWUtYTkyMi01Mjc5NmE0NTZiYzc="]
         * thumbnails : ["https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV80OWYzYmU1OS1hZmJiLTRhNDQtOGE1NS1iZDc4MDhiYmZiZjc=","https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV8zZmQ3ZjlkOC1iZmU0LTRjNTMtOTE0Yi05MTAwOThlZmUyMjY=","https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV9lOTEwYWQ5Yi1mYmU4LTQ5MDUtYTBkMC1lMTYyYmY3NGU3M2I=","https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV8yMWRlM2VjMi1hZDg2LTRjNDktOTBjZi1hZGQxZmUzMmM2MzE=","https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV83MjNmOTZmMS01MTVmLTQwMDgtOTBjYy0wNWZkNzhlNjQ3NGY=","https://nim.nosdn.127.net/NDMxOTM4MQ==/bmltYV82NDU2NTYxMzhfMTU1MzY1MTMxNTYzMV9jZDRhYWUxYy05Y2JmLTQ1OWUtYTkyMi01Mjc5NmE0NTZiYzc="]
         * hyperlinkimage : Icon
         * hyperlinkdescribe : 点我！点我！免费办理腾讯免流量大王卡、天王卡、地王卡！
         * hyperlinkurl : https%3A%2F%2Fm.10010.com%2Fqueen%2Ftencent%2Fking-tab.html%3Fchannel%3D62%26act_type%3DjXPAuZEJF5sW8RRofWbp9w%3D%3D%26id_type%3DqJKHBMChSUWopNbX1I%2B4Uw%3D%3D%26share_id%3DZyFQ36AnYK3Zstqen3iVmdCJsdf3424p5ypvPVGL36k%3D
         * assuredate : 100
         * price : 0.01
         * date : 2019-03-31 10:01:14
         * iscollect : 0
         * isstick : 0
         * islike : 0
         * likelist : []
         * commentlist : []
         * remark :
         * order_count : 0
         * isfixation : 1
         * repertory : 10000*/
        circle = bean.getCircle();
        CricleBean.DataBean data=new CricleBean.DataBean();
        data.setCircleid(circle.getCircleid());
        data.setCircleuid(circle.getCircleuid());
        data.setAvatar(circle.getAvatar());
        data.setName(circle.getName());
        data.setContent(circle.getContent());
        data.setImgs(circle.getImgs());
        data.setThumbnails(circle.getThumbnails());
        data.setHyperlinkimage(circle.getHyperlinkimage());
        data.setHyperlinkdescribe(circle.getHyperlinkdescribe());
        data.setHyperlinkurl(circle.getHyperlinkurl());
        data.setAssuredate(circle.getAssuredate());
        data.setPrice(circle.getPrice());
        data.setDate(circle.getDate());
        data.setIscollect(circle.getIscollect());
        data.setIsstick(circle.getIsstick());
        data.setIslike(circle.getIslike());
        data.setLikelist(circle.getLikelist());
        data.setCommentlist(circle.getCommentlist());
        data.setOrder_count(circle.getOrder_count());
        data.setRepertory(circle.getRepertory());

        list.add(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public String circlelisttype() {
        return "0";
    }


    @Override
    public void CircleAdv(CircleAdvertisementBean bean) {

        String generalizeimage = bean.getData().get(0).getGeneralizeimage();
        generalizeurl = bean.getData().get(0).getGeneralizeurl();
        Glide.with(getActivity())
                .load(generalizeimage)
                .into(imageView);
    }

    /**
     * 收藏和取消收藏
     */
    @Override
    public void collectSuccess(int returncode, String mess) {
        if (returncode == 0) {
            ToastUtil.showToast(mess);
        }


    }

    /**
     * 删除某个圈子
     */
    @Override
    public void DelectCircleSuccess(int returncode) {
        if (returncode == 0) {
            ToastUtil.showToast("删除成功");
        }
    }

    /**
     * 支付宝付款
     */
    @Override
    public void payToAlipay(CricleBuyPro buyPro) {
        doAlipay(buyPro);
    }

    /**
     * 积分
     */
    @Override
    public void payToIntegral(CricleBuyToInterial integralMessage) {
        ToastUtil.showToast(integralMessage.getMessage());
        /**圈子订单备注详情页*/
//               ARouter.getInstance().build("/activity/WebUserActivity")
//                       .withInt("tag",3)
//                       .withString("cricleid",integralMessage.getNewid())
//                       .navigation();
//        /*变换页面之后跳转备注页面*/
//        EventBusMessage eventBusMessage = new EventBusMessage(2, integralMessage.getNewid());
//        eventBusMessage.setMessage1(pusherid);
//        EventBus.getDefault().post(eventBusMessage);
        ARouter.getInstance().build("/activity/WebUserActivity")
                .withInt("tag",3)
                .withString("cricleid",integralMessage.getNewid())
                .withString("produceid",pusherid)
                .navigation();
    }

    /*获得V币*/
    @Override
    public void getV_money(CheckVisionBean checkVisionBean) {
        /**弹框支付提示框*/
        TextView tv_pay1, tv_pay2, tv_pay3, tv_pay4, tv_pay5, pay_cancel, tv_payWeixin;
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_pay_dialog, null);
        bottomSheet = new BottomSheetDialog(getActivity());//实例化BottomSheetDialog
        bottomSheet.setCancelable(true);//设置点击外部是否可以取消
        bottomSheet.setContentView(view);//设置对框框中的布局

        tv_pay1 = view.findViewById(R.id.tv_pay1);
        tv_pay2 = view.findViewById(R.id.tv_pay2);
        tv_pay3 = view.findViewById(R.id.tv_pay3);
        tv_pay4 = view.findViewById(R.id.tv_pay4);
        tv_pay5 = view.findViewById(R.id.tv_pay5);
        pay_cancel = view.findViewById(R.id.pay_cancel);
        tv_payWeixin = view.findViewById(R.id.tv_payWeixin);

        String str = "(" + price + "元）购买成功后有<font color='#FF0000'>" + days + "天" + "</font>担保期";
        tv_pay1.setText(Html.fromHtml(str));

        Double price1 = Double.parseDouble(price);
        Double price2 = Double.parseDouble(checkVisionBean.getVmoney());
        if (price1 > price2) {
            isprice = true;
            tv_pay3.setText(Html.fromHtml("V币支付（余额:" + checkVisionBean.getVmoney() + "元" + "<font color='#FF0000'>[余额不足]</font>)"));
        } else {
            isprice = false;
            tv_pay3.setText(Html.fromHtml("V币支付（余额:" + checkVisionBean.getVmoney() + "元" + "<font color='#FF0000'></font>)"));
        }


        tv_pay1.setOnClickListener(paylistener);
        tv_pay2.setOnClickListener(paylistener);
        tv_pay3.setOnClickListener(paylistener);
        tv_pay4.setOnClickListener(paylistener);
        tv_pay5.setOnClickListener(paylistener);
        pay_cancel.setOnClickListener(paylistener);
        tv_payWeixin.setOnClickListener(paylistener);
        bottomSheet.show();//显示弹窗
    }

    private CheckUpLoadDialog checkUpLoadDialog;
    private void showWeixinPayDialog() {
        String mess = "如果您支付宝支付不方便，可联系微信客服微信号：xiaoshulinapp 也可以点击咨询按钮找《客服小哥》询问微信支付相关方法。";
        checkUpLoadDialog = new CheckUpLoadDialog(getActivity(), "提示", mess, "取消", "咨询", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_cancel:
                        checkUpLoadDialog.dismiss();
                        break;
                    case R.id.tv_submit:
                        ParsingTools tools = new ParsingTools();
                        tools.SecondParseTool(getContext(), "http***ChatMessageListController");
                        checkUpLoadDialog.dismiss();
                        break;
                }
            }
        });
        checkUpLoadDialog.show();
    }


    WarningDialog dialogSupport;
    @Override
    public void enjoy(SupportBean bean) {
        /**欣赏回调接口*/
        String url = URLDecoder.decode(bean.getMessage());
        if (bean.getReturncode() == 0) {
            ToastUtil.showToast(url);
        } else {
            dialogSupport = new WarningDialog(getActivity(), url, "取消", "V币优充", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.tv_cancel:
                            dialogSupport.dismiss();
                            break;
                        case R.id.tv_submit:
                            ARouter.getInstance()
                                    .build("/activity/WebActivity")
                                    .withString("url", "http://www.vipbanlv.com/v2_test/#!/recharge")
                                    .navigation();
                            dialogSupport.dismiss();
                            break;
                    }
                }
            });
            dialogSupport.show();
        }
    }

    @Override
    public void payisOk(String newid) {
        /**支付宝支付成功后的确认接口回调*/
        /**圈子订单备注详情页*/
//        ARouter.getInstance().build("/activity/WebUserActivity")
//                .withInt("tag",3)
//                .withString("cricleid",newid)
//                .navigation();

//        EventBusMessage eventBusMessage = new EventBusMessage(2, newid);
//        eventBusMessage.setMessage1(pusherid);
//        EventBus.getDefault().post(eventBusMessage);
        ARouter.getInstance().build("/activity/WebUserActivity")
                .withInt("tag",3)
                .withString("cricleid",newid)
                .withString("produceid",pusherid)
                .navigation();
    }

    // 商户PID
    public static final String PARTNER = "2088621796126903";
    // 商户收款账号
    public static final String SELLER = "2867390698@qq.com";

    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALgwhRO7anREaLNFwt0V6Jc//dUJ4aUMso1g9qCvNEpBhQpGfw6JDcjqSDPwjD/XUSdxGeo0EY9C/gzHd+B7iI5a0iJlTVshKJmcUrssB1bL2pSwNGOpDCShpoTyk7SleZyXltWkEnJpVxoqWk3Xfxl4xQ594CKkckaWD+ExIGRlAgMBAAECgYBAbVhfSpM2ECe/bYt34g31ugCD4Gz5WrOqGFysYByeTMTIj1gqMW6M9MZExMOKITrV3Q3/ii8KOZi7x5n6/7sH15QQW2vFgd7BzD4VGuEUYYknXfAEJ7ljcVQtbIE4lTf/Ax+G9PEgfR096CoU/gmXT/+FuojVz+uXskycpNYGEQJBAPInhFgBTeDzzXc/MunCP4tRzRkyBdr2T+6SU3qLjvW53JH0Vk/I5yNkM4twNC4+Ny3fZsjl29lt4MVco3YRG5MCQQDCuI4Q6fKQm6y1cu5RTI8SGw/JIpglZYe/opzviZY53DOuRyAWxtNYMwMmFYGEGrikmywKC0qru6K5HH5wvqsnAkBitrFAnud8eGad4emDWWhGBwaw6q9wX1CeVJArOJECos3DwfMdeyyeRM6uXQMohrw/uv5Pj0RcEmq6idUo4Dh5AkEAkmjsQSF3Bm5XHROux6hWBxob83M0jSHbQSdG8wEz2IuOvGK0aQvUi4PHwVH4UVk6LmKSaXb2DUyHXMJk++cRjwJAHeBYuCdHkowGqpejZMJmluXaaxVVMm2HUntdhWsdDHxva3jplYB0m74VDkFDBJxAx/L40AF7LjTGrFRBko1qKA==";

    // 支付宝公钥
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;
    CricleBuyPro alipayBeans;

    private void doAlipay(CricleBuyPro alipayBean) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(getActivity()).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            getActivity().finish();
                        }
                    }).show();
            return;
        }
        alipayBeans = alipayBean;

        String orderInfo = getOrderInfo(alipayBean.getTitle(), alipayBean.getTitle(), String.valueOf(alipayBean.getPrice()), alipayBean.getOrderid());

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(getActivity());
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }


    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price, String orderid) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderid + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                        presenter.PaySuccessOk(alipayBeans.getOrderid(), alipayBeans.getPaytype());

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(getActivity(), "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private View.OnClickListener paylistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_pay1:

                    bottomSheet.dismiss();
                    break;
                case R.id.tv_pay2:
                    /**支付宝支付*/
                    presenter.BuyPro(Circleid, "1");
                    bottomSheet.dismiss();
                    break;
                case R.id.tv_payWeixin:
                    /**微信支付*/
                    showWeixinPayDialog();
                    bottomSheet.dismiss();
                    break;
                case R.id.tv_pay3:
                    /**余额不足的时候*/
                    if (isprice) {
                        ARouter.getInstance()
                                .build("/activity/WebActivity")
                                .withString("url", "http://www.vipbanlv.com/v2_test/#!/recharge")
                                .navigation();
                    } else {
                        presenter.BuyPro(Circleid, "5");
                    }
                    bottomSheet.dismiss();
                    break;
                case R.id.tv_pay4:
                    /**V币优充*/
                    ARouter.getInstance()
                            .build("/activity/WebActivity")
                            .withString("url", "http://www.vipbanlv.com/v2_test/#!/recharge")
                            .navigation();
                    bottomSheet.dismiss();
                    break;
                case R.id.tv_pay5:
                    /**分享*/
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
                    bottomSheet.dismiss();
                    break;
                case R.id.pay_cancel:
                    bottomSheet.dismiss();
                    break;
            }
        }
    };
}
