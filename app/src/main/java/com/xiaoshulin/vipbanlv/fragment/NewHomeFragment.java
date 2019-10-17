package com.xiaoshulin.vipbanlv.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ViewFlipper;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.NewHomeFragmentBean;
import com.xiaoshulin.vipbanlv.NewHomefrags.adapter.NewHomeFragmentAdapter;
import com.xiaoshulin.vipbanlv.presenter.NewHomeFragmentPresenter;
import com.xiaoshulin.vipbanlv.view.INewHomeFragmentView;

import java.util.List;

/**
 * 新的首页UI页面
 */
public class NewHomeFragment extends BaseMVPFragment implements INewHomeFragmentView {

    NewHomeFragmentPresenter presenter;

    XRecyclerView home_new_recycle;

    NewHomeFragmentAdapter newHomeFragmentAdapter;

    private ViewFlipper view_flipper;

    public static NewHomeFragment newInstance() {
        NewHomeFragment fragment = new NewHomeFragment();
        return fragment;
    }

    public NewHomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initData() {
        //获取首页数据
        presenter.getHomeData();

    }

    @Override
    protected void initView() {
        home_new_recycle = mRootView.findViewById(R.id.home_new_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        home_new_recycle.setLayoutManager(layoutManager);
        newHomeFragmentAdapter=new NewHomeFragmentAdapter(getActivity());
        home_new_recycle.setAdapter(newHomeFragmentAdapter);

        view_flipper = (ViewFlipper) mRootView.findViewById(R.id.view_flipper);
        for (int i = 0; i <4; i++) {
            View view = getLayoutInflater().inflate(R.layout.new_home_adv_item_layout,null);
            view_flipper.addView(view);
        }
        view_flipper.setFlipInterval(2000);
        view_flipper.startFlipping();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_home;
    }

    @Override
    public BasePresenter initPresenter() {
        presenter = new NewHomeFragmentPresenter(this);
        return presenter;
    }

    @Override
    public void getNewHomeFragmentData(NewHomeFragmentBean bean) {
        //首页接口数据返回

        showHomeUI(bean);

    }

    private void showHomeUI(NewHomeFragmentBean bean) {
        List<NewHomeFragmentBean> list = newHomeFragmentAdapter.getList();

        NewHomeFragmentBean firstbean=new NewHomeFragmentBean();
        firstbean.setToplist(bean.getToplist());
        firstbean.setToplisttime(bean.getToplisttime());
        list.add(firstbean);

        NewHomeFragmentBean secondbean=new NewHomeFragmentBean();
        secondbean.setNewsdata(bean.getNewsdata());
        secondbean.setNewsdatatime(bean.getNewsdatatime());
        list.add(secondbean);

        NewHomeFragmentBean thirdbean=new NewHomeFragmentBean();
        thirdbean.setGeneralizelist(bean.getGeneralizelist());
        thirdbean.setGeneralizelisttime(bean.getGeneralizelisttime());
        list.add(thirdbean);

        NewHomeFragmentBean fourthbean=new NewHomeFragmentBean();
        fourthbean.setVlivelist(bean.getVlivelist());
        fourthbean.setVlivelisttime(bean.getVlivelisttime());
        list.add(fourthbean);

        newHomeFragmentAdapter.notifyDataSetChanged();

    }
}
