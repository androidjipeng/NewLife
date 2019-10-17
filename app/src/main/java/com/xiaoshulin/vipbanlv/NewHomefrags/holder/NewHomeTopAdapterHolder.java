package com.xiaoshulin.vipbanlv.NewHomefrags.holder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoshulin.vipbanlv.NewHomefrags.homebean.TopHomeBean;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseViewHolder;

/**
 * Created by jipeng on 2019-10-16 17:45.
 */
public class NewHomeTopAdapterHolder extends BaseViewHolder {
    Activity activity;
    ImageView img_top_home;
    TextView tv_top_home_title;

    public NewHomeTopAdapterHolder(@NonNull Activity activity, @NonNull View itemView) {
        super(activity, itemView);
        this.activity = activity;
        img_top_home = itemView.findViewById(R.id.img_top_home);
        tv_top_home_title = itemView.findViewById(R.id.tv_top_home_title);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);
        TopHomeBean bean = (TopHomeBean) obj;
        Glide.with(activity)
                .load(bean.getIcon())
                .into(img_top_home);
        tv_top_home_title.setText(bean.getTitle());
    }
}
