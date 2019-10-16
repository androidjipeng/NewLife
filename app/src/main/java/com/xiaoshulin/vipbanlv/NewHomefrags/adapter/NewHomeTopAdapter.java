package com.xiaoshulin.vipbanlv.NewHomefrags.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoshulin.vipbanlv.NewHomefrags.holder.NewHomeTopAdapterHolder;
import com.xiaoshulin.vipbanlv.NewHomefrags.homebean.TopHomeListBean;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jipeng on 2019-10-16 17:35.
 */
public class NewHomeTopAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    List<TopHomeListBean> list=new ArrayList<>();

    Activity activity;

    public NewHomeTopAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_new_home_top_viewpager_item_layout,parent,false);
        NewHomeTopAdapterHolder homeTopAdapterHolder=new NewHomeTopAdapterHolder(activity,view);
        return homeTopAdapterHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
          holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
         return list != null ? list.size() : 0;
    }

    public List<TopHomeListBean> getList() {
        return list;
    }
}
