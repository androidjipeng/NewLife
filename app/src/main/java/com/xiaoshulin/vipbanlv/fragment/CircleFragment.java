package com.xiaoshulin.vipbanlv.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;

import com.xiaoshulin.vipbanlv.bean.EventBusMessage;
import com.xiaoshulin.vipbanlv.circleFrags.MyCircleAdapter;
import com.xiaoshulin.vipbanlv.presenter.CirclePresenter;
import com.xiaoshulin.vipbanlv.view.ICircleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 圈子 2018-12-18 Created jipeng
 */
public class CircleFragment extends BaseMVPFragment implements ICircleView,View.OnClickListener {

    private CirclePresenter presenter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button attention;
    private MyCircleAdapter myCircleAdapter;
    public CircleFragment() {
        // Required empty public constructor
    }

    public static CircleFragment newInstance() {
        CircleFragment fragment = new CircleFragment();
        return fragment;
    }


    @Override
    protected void initData() {
        //添加标签
        tabLayout.addTab(tabLayout.newTab().setText("最新"),0);
        tabLayout.addTab(tabLayout.newTab().setText("推荐"),1);
        tabLayout.addTab(tabLayout.newTab().setText("收藏"),2);
        tabLayout.addTab(tabLayout.newTab().setText("订单"),3);
        tabLayout.addTab(tabLayout.newTab().setText("我的"),4);
        myCircleAdapter = new MyCircleAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(myCircleAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void initView() {
        tabLayout = mRootView.findViewById(R.id.tab_layout);
        viewPager = mRootView.findViewById(R.id.view_pager);
        attention=mRootView.findViewById(R.id.attention);
        attention.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    public BasePresenter initPresenter() {
        presenter = new CirclePresenter(this);
        return presenter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.attention:
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url", "https://mp.weixin.qq.com/s/4IcgzXzFC076WXTahGdvIA")
                        .navigation();
                break;
        }
    }


    /**eventbus更新通知事件*/
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessage event) {
        // 更新界面
        if (event.getType()==2)
        {

            viewPager.setCurrentItem(3);
            tabLayout.getTabAt(3).select();
            ARouter.getInstance().build("/activity/WebUserActivity")
                    .withInt("tag",3)
                    .withString("cricleid",event.getMessage())
                    .withString("produceid",event.getMessage1())
                    .navigation();
            EventBus.getDefault().cancelEventDelivery(event) ;  //取消事件传递
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
}
