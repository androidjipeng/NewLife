package com.xiaoshulin.vipbanlv.fragment;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.activity.OrderListActivity;
import com.xiaoshulin.vipbanlv.adapter.MainAdapter;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.MainDataBean;
import com.xiaoshulin.vipbanlv.bean.VipFloatInformBean;
import com.xiaoshulin.vipbanlv.presenter.HomeFragmentPresenter;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.ToastUtil;
import com.xiaoshulin.vipbanlv.view.HomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseMVPFragment implements HomeView, View.OnClickListener {
    private RecyclerView home_recycle;
    HomeFragmentPresenter presenter;
    private MainAdapter adapter;
    private ImageView vip_img;
    private VipFloatInformBean vipFloatInformBean;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    protected void initData() {
        presenter.getDataInformation();
    }

    @Override
    protected void initView() {
        home_recycle = mRootView.findViewById(R.id.home_recycle);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        home_recycle.setLayoutManager(manager);


        vip_img = mRootView.findViewById(R.id.vip_img);
        vip_img.setOnClickListener(this);


        String vipIcon = SharePreferenceUtil.getinstance().getVipIcon();
        Gson gson=new Gson();
        vipFloatInformBean = gson.fromJson(vipIcon, VipFloatInformBean.class);
        imgagelocation();

        Glide.with(getActivity())
                .load(vipFloatInformBean.getGeneralizeicon())
                .into(vip_img);
    }

    private void imgagelocation() {

        String generalizelocation = vipFloatInformBean.getGeneralizelocation();
        String[] split = generalizelocation.split("\\*");

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();



        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) vip_img.getLayoutParams();
        params.height = Integer.parseInt(split[3])*2;
        params.width = Integer.parseInt(split[2])*2;


        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(vip_img.getLayoutParams());
        Log.e("jp", "--------imgagelocation:   width:"+Double.parseDouble(split[0])+"        "+width);
        Log.e("jp", "--------imgagelocation:   height:"+Double.parseDouble(split[1])+"       "+height);
        double left= Double.parseDouble(split[0])*width;
        double top=Double.parseDouble(split[1])*height;
        margin.leftMargin=(int)left;
        margin.topMargin=(int)top;

        Log.e("jp", "--------imgagelocation:   margin.leftMargin:"+(int)left);
        Log.e("jp", "--------imgagelocation:   margin.topMargin:"+(int)top);

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(margin);
        vip_img.setLayoutParams(params1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new HomeFragmentPresenter(this);
    }

    @Override
    public void getListData(ArrayList<MainDataBean> items) {
        adapter = new MainAdapter(items);
        home_recycle.setAdapter(adapter);

        adapterListener(items);
    }

    private void adapterListener(ArrayList<MainDataBean> items) {
        adapter.setOnItemClickLitener(new MainAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 2) {
                    /**订单*/
                    Intent intent = new Intent(getActivity(), OrderListActivity.class);
                    startActivity(intent);

                } else if (position == 3) {
                    /**出租会员账号*/
                    ARouter.getInstance()
                            .build("/user/UserInformation")
                            .withInt("tag", 13)
                            .navigation();
                } else if (position == 7) {
                    /**小树林支付宝客服*/
//                    jumpAlipay(ToastUtil.zhifubao());
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
                } else if (position == 11) {
                    /**小树林分享*/
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

                } else {
                    /**进入web由html5的方式展示*/
                    ARouter.getInstance()
                            .build("/activity/WebActivity")
                            .withString("url", items.get(position).getUrl())
                            .navigation();

//                    ParsingTools tools=new ParsingTools();
//                    tools.SecondParseTool(getContext(),"https://mp.weixin.qq.com/*888**2c10d3f0@t,8$t1,0$u,http*1**http://pr.binglunhrq.com/back/20181213/3b4a8ea7f9e49e12673e01e0e110d85c.mp4$i,美女视频等你欣赏*888**");


                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vip_img:
                ParsingTools tools=new ParsingTools();
                tools.SecondParseTool(getContext(),vipFloatInformBean.getGeneralizeurl());
                break;
        }
    }
}
