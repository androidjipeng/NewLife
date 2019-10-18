package com.xiaoshulin.vipbanlv.NewHomefrags.holder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseViewHolder;
import com.xiaoshulin.vipbanlv.bean.NewHomeFragmentBean;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;

import java.util.List;

/**
 * Created by jipeng on 2019-10-15 11:27.
 */
public class NewHomeFragmentAdaptersSecondItemHolder extends BaseViewHolder {

    ImageView img_second;
    TextView tv_second_content1;
    TextView tv_second_content2;
    public NewHomeFragmentAdaptersSecondItemHolder(@NonNull Activity activity, @NonNull View itemView) {
        super(activity, itemView);
        img_second=itemView.findViewById(R.id.img_second);
        tv_second_content1=itemView.findViewById(R.id.tv_second_content1);
        tv_second_content2=itemView.findViewById(R.id.tv_second_content2);
    }

    @Override
    public void setData(Object obj) {
        super.setData(obj);
        NewHomeFragmentBean bean= (NewHomeFragmentBean) obj;

        showUI(bean.getNewsdata());

    }

    private void showUI(NewHomeFragmentBean.NewsdataBean newsdata) {
        Log.e("jp","     newsdata.getIcon:::       "+newsdata.getIcon());
//        Glide.with(activity)
//                .load(newsdata.getIcon())
//                .into(img_second);
        List<NewHomeFragmentBean.NewsdataBean.NewslistBean> newslist = newsdata.getNewslist();
        for (int i = 0; i <newslist.size(); i++) {
            if (i==0){
                tv_second_content1.setText("1."+newslist.get(i).getTitle());
            }else if (i==1){
                tv_second_content2.setText("2."+newslist.get(i).getTitle());
            }
        }

        tv_second_content1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newslist.size()>1){
                    ParsingTools tools=new ParsingTools();
                    tools.SecondParseTool(activity,newslist.get(0).getUrl());
//                    ParsingTools.FirstParseTool(newslist.get(0).getUrl());
                }

            }
        });

        tv_second_content1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newslist.size()>=2){
                    ParsingTools tools=new ParsingTools();
                    tools.SecondParseTool(activity,newslist.get(1).getUrl());
//                    ParsingTools.FirstParseTool(newslist.get(1).getUrl());
                }

            }
        });
    }
}
