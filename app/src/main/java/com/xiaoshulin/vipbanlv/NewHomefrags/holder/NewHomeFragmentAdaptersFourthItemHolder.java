package com.xiaoshulin.vipbanlv.NewHomefrags.holder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xiaoshulin.vipbanlv.NewHomefrags.adapter.NewHomeFourthAdapter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseViewHolder;
import com.xiaoshulin.vipbanlv.bean.NewHomeFragmentBean;

import java.util.List;

/**
 * Created by jipeng on 2019-10-15 11:27.
 */
public class NewHomeFragmentAdaptersFourthItemHolder extends BaseViewHolder {

    RecyclerView fourth_recycle;
    Activity activity;
    NewHomeFourthAdapter adapter;

    public NewHomeFragmentAdaptersFourthItemHolder(@NonNull Activity activity, @NonNull View itemView) {
        super(activity, itemView);
        this.activity = activity;
        fourth_recycle = itemView.findViewById(R.id.fourth_recycle);
        GridLayoutManager layoutManager = new GridLayoutManager(activity, 2);
        fourth_recycle.setLayoutManager(layoutManager);
        adapter = new NewHomeFourthAdapter(activity);
        fourth_recycle.setAdapter(adapter);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);
        NewHomeFragmentBean bean = (NewHomeFragmentBean) obj;
        showUI(bean.getVlivelist());
    }

    private void showUI(List<NewHomeFragmentBean.VlivelistBean> vlivelist) {
              adapter.getList().addAll(vlivelist);
              adapter.notifyDataSetChanged();
    }
}
