package com.xiaoshulin.vipbanlv.NewHomefrags.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseViewHolder;
import com.xiaoshulin.vipbanlv.bean.NewHomeFragmentBean;
import com.xiaoshulin.vipbanlv.NewHomefrags.holder.NewHomeFragmentAdapterTopItemHolder;
import com.xiaoshulin.vipbanlv.NewHomefrags.holder.NewHomeFragmentAdaptersFourthItemHolder;
import com.xiaoshulin.vipbanlv.NewHomefrags.holder.NewHomeFragmentAdaptersSecondItemHolder;
import com.xiaoshulin.vipbanlv.NewHomefrags.holder.NewHomeFragmentAdaptersThirdItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jipeng on 2019-10-15 11:09.
 */
public class NewHomeFragmentAdapter extends RecyclerView.Adapter<BaseViewHolder>{

    List<NewHomeFragmentBean> list=new ArrayList<>();

    Activity activity;
    public NewHomeFragmentAdapter(Activity activity) {
     this.activity=activity;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0){
            View view= LayoutInflater.from(activity).inflate(R.layout.fragment_new_home_top_item_layout,parent,false);
            NewHomeFragmentAdapterTopItemHolder homeFragmentAdapterTopItemHolder=new NewHomeFragmentAdapterTopItemHolder(activity,view);
            return homeFragmentAdapterTopItemHolder;
        }else if (viewType==1){
            View view= LayoutInflater.from(activity).inflate(R.layout.fragment_new_home_second_item_layout,parent,false);
            NewHomeFragmentAdaptersSecondItemHolder homeFragmentAdaptersSecondItemHolder=new NewHomeFragmentAdaptersSecondItemHolder(activity,view);
            return homeFragmentAdaptersSecondItemHolder;
        }else if (viewType==2){
            View view= LayoutInflater.from(activity).inflate(R.layout.fragment_new_home_third_item_layout,parent,false);
            NewHomeFragmentAdaptersThirdItemHolder homeFragmentAdaptersThirdItemHolder=new NewHomeFragmentAdaptersThirdItemHolder(activity,view);
            return homeFragmentAdaptersThirdItemHolder;
        }else if (viewType==3){
            View view= LayoutInflater.from(activity).inflate(R.layout.fragment_new_home_fourth_item_layout,parent,false);
            NewHomeFragmentAdaptersFourthItemHolder homeFragmentAdaptersFourthItemHolder=new NewHomeFragmentAdaptersFourthItemHolder(activity,view);
            return homeFragmentAdaptersFourthItemHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
               holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 0;
        }else if (position==1){
            return 1;
        }else if (position==2){
            return 2;
        }else if (position==3){
            return 3;
        }
        return super.getItemViewType(position);
    }


    public List<NewHomeFragmentBean> getList() {
        return list;
    }


}
