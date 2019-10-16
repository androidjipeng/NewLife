package com.xiaoshulin.vipbanlv.NewHomefrags.holder;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.tmall.ultraviewpager.UltraViewPager;
import com.xiaoshulin.vipbanlv.NewHomefrags.adapter.TopPagerAdapter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jipeng on 2019-10-15 11:27.
 */
public class NewHomeFragmentAdapterTopItemHolder extends BaseViewHolder {

    UltraViewPager ultra_viewpager;

    public NewHomeFragmentAdapterTopItemHolder(@NonNull Activity activity, @NonNull View itemView) {
        super(activity, itemView);
        ultra_viewpager = itemView.findViewById(R.id.ultra_viewpager);

        initUI();
    }

    private void initUI() {
        ultra_viewpager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        //UltraPagerAdapter 绑定子view到UltraViewPager

        List<View> list=new ArrayList<>();
        View view= LayoutInflater.from(activity).inflate(R.layout.fragment_home_top_itme_viewpager_layout,null,false);
        RecyclerView top_recycle=view.findViewById(R.id.top_recycle);
        GridLayoutManager layoutManager = new GridLayoutManager(activity,4);
        top_recycle.setLayoutManager(layoutManager);





        View view1= LayoutInflater.from(activity).inflate(R.layout.fragment_home_top_itme_viewpager_layout,null,false);
        list.add(view);
        list.add(view1);
        PagerAdapter adapter = new TopPagerAdapter(list);
        ultra_viewpager.setAdapter(adapter);

        //内置indicator初始化
        ultra_viewpager.initIndicator();
        //设置indicator样式
        ultra_viewpager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.GREEN)
                .setNormalColor(Color.WHITE)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, activity.getResources().getDisplayMetrics()));
        //设置indicator对齐方式
        ultra_viewpager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //构造indicator,绑定到UltraViewPager
        ultra_viewpager.getIndicator().build();
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);
    }
}
