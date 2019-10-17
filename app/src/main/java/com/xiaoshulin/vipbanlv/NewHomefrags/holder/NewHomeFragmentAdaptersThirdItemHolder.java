package com.xiaoshulin.vipbanlv.NewHomefrags.holder;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tmall.ultraviewpager.UltraViewPager;
import com.xiaoshulin.vipbanlv.NewHomefrags.adapter.ThirdPagerAdapter;
import com.xiaoshulin.vipbanlv.NewHomefrags.adapter.TopPagerAdapter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseViewHolder;
import com.xiaoshulin.vipbanlv.bean.NewHomeFragmentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jipeng on 2019-10-15 11:27.
 */
public class NewHomeFragmentAdaptersThirdItemHolder extends BaseViewHolder {
    Activity activity;
    UltraViewPager ultra_second_viewpager;
    public NewHomeFragmentAdaptersThirdItemHolder(@NonNull Activity activity, @NonNull View itemView) {
        super(activity, itemView);
        this.activity=activity;
        ultra_second_viewpager=itemView.findViewById(R.id.ultra_second_viewpager);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);
        NewHomeFragmentBean bean= (NewHomeFragmentBean) obj;
        showUI(bean.getGeneralizelist());
    }


    private void showUI(List<NewHomeFragmentBean.GeneralizelistBean> generalizelist) {
        Log.e("jp","    generalizelist::third----   "+generalizelist.size());
        ultra_second_viewpager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);

        PagerAdapter adapter = new ThirdPagerAdapter(activity,generalizelist);
        ultra_second_viewpager.setAdapter(adapter);

        //内置indicator初始化
        ultra_second_viewpager.initIndicator();
        //设置indicator样式
        ultra_second_viewpager.getIndicator()
                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                .setFocusColor(Color.GREEN)
                .setNormalColor(Color.WHITE)
                .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, activity.getResources().getDisplayMetrics()));
        //设置indicator对齐方式
        ultra_second_viewpager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        //构造indicator,绑定到UltraViewPager
        ultra_second_viewpager.getIndicator().build();

        //设定页面循环播放
        ultra_second_viewpager.setInfiniteLoop(true);
        //设定页面自动切换  间隔2秒
        ultra_second_viewpager.setAutoScroll(2000);

    }
}
