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
import com.xiaoshulin.vipbanlv.bean.NewHomeFragmentBean;

/**
 * Created by jipeng on 2019-10-16 17:45.
 */
public class NewHomeFourthAdapterHolder extends BaseViewHolder {
    Activity activity;
    ImageView img_fourth_home;
    TextView tv_fourth_title;
    TextView tv_fourth_content;

    public NewHomeFourthAdapterHolder(@NonNull Activity activity, @NonNull View itemView) {
        super(activity, itemView);
        this.activity = activity;
        img_fourth_home = itemView.findViewById(R.id.img_fourth_home);
        tv_fourth_title = itemView.findViewById(R.id.tv_fourth_title);
        tv_fourth_content=itemView.findViewById(R.id.tv_fourth_content);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);
        NewHomeFragmentBean.VlivelistBean bean = (NewHomeFragmentBean.VlivelistBean) obj;
        Glide.with(activity)
                .load(bean.getIcon())
                .into(img_fourth_home);
        tv_fourth_title.setText(bean.getTitle());
        tv_fourth_content.setText(bean.getDescribe());
    }
}
