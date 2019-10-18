package com.xiaoshulin.vipbanlv.NewHomefrags.holder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoshulin.vipbanlv.NewHomefrags.homebean.TopHomeBean;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseViewHolder;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;

/**
 * Created by jipeng on 2019-10-16 17:45.
 */
public class NewHomeTopAdapterHolder extends BaseViewHolder {
    Activity activity;
    ImageView img_top_home;
    TextView tv_top_home_title;
    LinearLayout ll_top_item;
    TopHomeBean bean;
    public NewHomeTopAdapterHolder(@NonNull Activity activity, @NonNull View itemView) {
        super(activity, itemView);
        this.activity = activity;
        img_top_home = itemView.findViewById(R.id.img_top_home);
        tv_top_home_title = itemView.findViewById(R.id.tv_top_home_title);

        ll_top_item=itemView.findViewById(R.id.ll_top_item);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);
        bean = (TopHomeBean) obj;

        showUI();


    }

    private void showUI() {
        Glide.with(activity)
                .load(bean.getIcon())
                .into(img_top_home);
        tv_top_home_title.setText(bean.getTitle());


        ll_top_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParsingTools tools=new ParsingTools();
                tools.SecondParseTool(activity,bean.getUrl());
//                ParsingTools.FirstParseTool(bean.getUrl());
            }
        });
    }


}
