package com.xiaoshulin.vipbanlv.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.adapter.OrderListAdapter;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.OrderListBean;
import com.xiaoshulin.vipbanlv.presenter.OrderLIstPresenter;
import com.xiaoshulin.vipbanlv.view.OrderListVIew;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseMVPFragment implements OrderListVIew,View.OnClickListener{

    RelativeLayout relativeLayout;
    TextView btn_web;
    private OrderLIstPresenter presenter;
    private RecyclerView Order_recycle;
    OrderListAdapter adapter=null;
    public OrderFragment() {
        // Required empty public constructor
    }
    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.orderListData();
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        relativeLayout =mRootView.findViewById(R.id.rela);
        btn_web = mRootView.findViewById(R.id.btnweb);
        btn_web.setOnClickListener(this);

        Order_recycle = mRootView.findViewById(R.id.Order_recycle);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        Order_recycle.setLayoutManager(manager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new OrderLIstPresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnweb:
                String url="http://www.vipbanlv.com";
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url", url)
                        .navigation();
                break;
            default:
                break;
        }
    }

    @Override
    public void getOrderListData(OrderListBean bean) {
        if (adapter==null)
        {
            adapter = new OrderListAdapter(getActivity(), bean.getList());
            Order_recycle.setAdapter(adapter);
        }else {
            adapter.setList(bean.getList());
            adapter.notifyDataSetChanged();
        }

        if (bean.getList().size() == 0) {
            relativeLayout.setVisibility(View.VISIBLE);
        }else {
            relativeLayout.setVisibility(View.GONE);
        }
    }
}
