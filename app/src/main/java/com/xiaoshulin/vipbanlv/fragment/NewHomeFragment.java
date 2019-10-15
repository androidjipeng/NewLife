package com.xiaoshulin.vipbanlv.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.fragment.adapter.NewHomeFragmentAdapter;
import com.xiaoshulin.vipbanlv.presenter.NewHomeFragmentPresenter;
import com.xiaoshulin.vipbanlv.view.INewHomeFragmentView;

/**
 * 新的首页UI页面
 */
public class NewHomeFragment extends BaseMVPFragment implements INewHomeFragmentView {

    NewHomeFragmentPresenter presenter;

    XRecyclerView home_new_recycle;

    NewHomeFragmentAdapter newHomeFragmentAdapter;

    public static NewHomeFragment newInstance() {
        NewHomeFragment fragment = new NewHomeFragment();
        return fragment;
    }

    public NewHomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initData() {
        presenter.getHomeData();
        for (int i = 0; i <4 ; i++) {
            newHomeFragmentAdapter.getList().add("");
        }
        newHomeFragmentAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        home_new_recycle = mRootView.findViewById(R.id.home_new_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        home_new_recycle.setLayoutManager(layoutManager);
        newHomeFragmentAdapter=new NewHomeFragmentAdapter(getActivity());
        home_new_recycle.setAdapter(newHomeFragmentAdapter);

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
}
