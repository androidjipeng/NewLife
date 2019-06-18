package com.xiaoshulin.vipbanlv.circleFrags.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.xiaoshulin.vipbanlv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jipeng on 2018/12/20.
 */
public class CriclePicGradviewAdapter extends BaseAdapter {

    private List<String> urls;
    Context context;

    public CriclePicGradviewAdapter(Context context,List<String> urls) {
        this.urls = urls;
        this.context = context;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int i) {
        return urls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyviewHodler hodler;
        if (view==null)
        {
            view=LayoutInflater.from(context).inflate(R.layout.cricle_picture_item_layout,viewGroup,false);
            hodler=new MyviewHodler();
            hodler.imageView=view.findViewById(R.id.img_pic);

            view.setTag(hodler);
        }else {
            hodler= (MyviewHodler) view.getTag();
        }

        Glide.with(context)
                .load(urls.get(i))
                .into(hodler.imageView);

        return view;
    }

    class MyviewHodler{
        ImageView imageView;
    }
}
