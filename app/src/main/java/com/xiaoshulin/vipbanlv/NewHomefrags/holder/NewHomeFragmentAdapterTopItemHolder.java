package com.xiaoshulin.vipbanlv.NewHomefrags.holder;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.tmall.ultraviewpager.UltraViewPager;
import com.xiaoshulin.vipbanlv.NewHomefrags.adapter.NewHomeTopAdapter;
import com.xiaoshulin.vipbanlv.NewHomefrags.adapter.TopPagerAdapter;
import com.xiaoshulin.vipbanlv.NewHomefrags.homebean.TopHomeBean;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseViewHolder;
import com.xiaoshulin.vipbanlv.bean.NewHomeFragmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jipeng on 2019-10-15 11:27.
 */
public class NewHomeFragmentAdapterTopItemHolder extends BaseViewHolder {

    UltraViewPager ultra_viewpager;
    NewHomeFragmentBean bean;
    public NewHomeFragmentAdapterTopItemHolder(@NonNull Activity activity, @NonNull View itemView) {
        super(activity, itemView);
        ultra_viewpager = itemView.findViewById(R.id.ultra_viewpager);


    }

    private void initUI() {
        ultra_viewpager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //UltraPagerAdapter 绑定子view到UltraViewPager

        List<View> list=new ArrayList<>();
        //top数据集合
        List<NewHomeFragmentBean.ToplistBean> toplist = bean.getToplist();


        View view= LayoutInflater.from(activity).inflate(R.layout.fragment_home_top_itme_viewpager_layout,null,false);
        RecyclerView top_recycle=view.findViewById(R.id.top_recycle);
        GridLayoutManager layoutManager = new GridLayoutManager(activity,4);
        top_recycle.setLayoutManager(layoutManager);
        NewHomeTopAdapter newHomeTopAdapter=new NewHomeTopAdapter(activity);
        top_recycle.setAdapter(newHomeTopAdapter);



        View view1= LayoutInflater.from(activity).inflate(R.layout.fragment_home_top_itme_viewpager_layout,null,false);
        RecyclerView top_recycle1=view1.findViewById(R.id.top_recycle);
        GridLayoutManager layoutManager1 = new GridLayoutManager(activity,4);
        top_recycle1.setLayoutManager(layoutManager1);
        NewHomeTopAdapter newHomeTopAdapter1=new NewHomeTopAdapter(activity);
        top_recycle1.setAdapter(newHomeTopAdapter1);

        List<TopHomeBean> topHomeBeanlist = newHomeTopAdapter.getList();
        List<TopHomeBean> topHomeBeanlist1 = newHomeTopAdapter1.getList();


        for (int i = 0; i <toplist.size(); i++) {

            TopHomeBean topHomeBean=new TopHomeBean(toplist.get(i).getTitle(),toplist.get(i).getIcon(),toplist.get(i).getUrl());
            if (i<12){
                //装入adapter的集合中
                topHomeBeanlist.add(topHomeBean);
//                Log.e("jp","---------childItemlist00000000:::"+topHomeBeanlist.size());
            }else {
                topHomeBeanlist1.add(topHomeBean);
//                Log.e("jp","---------childItemlist1111111:::"+topHomeBeanlist1.size());
            }

        }

        newHomeTopAdapter.notifyDataSetChanged();
        newHomeTopAdapter1.notifyDataSetChanged();


        list.add(view);
        list.add(view1);
        PagerAdapter adapter = new TopPagerAdapter(list);
        ultra_viewpager.setAdapter(adapter);

        //内置indicator初始化
        ultra_viewpager.initIndicator();
        //设置indicator样式
        ultra_viewpager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.RED)
                .setNormalColor(Color.GRAY)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, activity.getResources().getDisplayMetrics()));
        //设置indicator对齐方式
        ultra_viewpager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //构造indicator,绑定到UltraViewPager
        ultra_viewpager.getIndicator().build();
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);
        bean= (NewHomeFragmentBean) obj;
        initUI();
    }
}
