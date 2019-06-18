package com.xiaoshulin.vipbanlv.image;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.xiaoshulin.vipbanlv.R;

import java.io.File;
import java.util.List;



public class ImagePreviewAdapter extends PagerAdapter {

    private Context context;
    private List<String> data;

    public ImagePreviewAdapter(Context context) {
        this.context = context;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {

        View view = container.inflate(container.getContext(), R.layout.preview_pic_layout, null);

        SubsamplingScaleImageView imageView = view.findViewById(R.id.image_preview_image);

        Glide.with(context)
                .load(data.get(position))
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        imageView.setImage(ImageSource.uri(resource.getAbsolutePath()));
                    }
                });

        container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}