package com.xiaoshulin.vipbanlv.NewHomefrags.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.bean.NewHomeFragmentBean;

import java.util.List;

/**
 * Created by jipeng on 2019-10-16 15:27.
 */
public class ThirdPagerAdapter extends PagerAdapter {

    List<NewHomeFragmentBean.GeneralizelistBean> list;
    Activity activity;
    public ThirdPagerAdapter(Activity activity,List<NewHomeFragmentBean.GeneralizelistBean> list) {
        this.list = list;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(activity).inflate(R.layout.fragment_new_home_third_viewpage_item_layout,null,false);
        ImageView imageView=view.findViewById(R.id.img_third_bg);
        Glide.with(activity)
                .load(list.get(position).getIcon())
                .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
