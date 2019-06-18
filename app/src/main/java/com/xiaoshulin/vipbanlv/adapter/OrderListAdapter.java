package com.xiaoshulin.vipbanlv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;

import java.util.List;

import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.bean.OrderListBean;

/**
 * Created by jipeng on 2018/5/19.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHodler>{

    private List<OrderListBean.ListBean> list;
    Context context;

    public List<OrderListBean.ListBean> getList() {
        return list;
    }

    public void setList(List<OrderListBean.ListBean> list) {
        this.list = list;
    }

    public OrderListAdapter(Context context, List<OrderListBean.ListBean> list) {
        this.list = list;
        this.context=context;
    }

    @Override
    public OrderViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item_layout,parent,false);
        OrderViewHodler hodler=new OrderViewHodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(OrderViewHodler holder, int position) {

        holder.order_title.setText(list.get(position).getName());
        holder.date.setText(list.get(position).getDayend()+"");
        Glide.with(context)
                .load(list.get(position).getImg())
                .into(holder.img_loge);
        OrderListBean.ListBean listBean = list.get(position);

        holder.relativelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build("/activity/OrderInformationActivity")
                        .withString("Name",listBean.getName())
                        .withString("Dayend",listBean.getDayend())
                        .withString("Wechat",listBean.getWechat())
                        .withString("Orderid",listBean.getUsername())
                        .withString("Password",listBean.getPassword())
                        .withString("pushuid",listBean.getPushuid())
                        .withString("pushwechat",listBean.getPushwechat())
                        .withInt("tag",1)
                        .navigation();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class OrderViewHodler extends RecyclerView.ViewHolder
    {

        TextView order_title,date;

        ImageView img_loge;

        RelativeLayout relativelayout;

        public OrderViewHodler(View itemView) {
            super(itemView);

            order_title=itemView.findViewById(R.id.order_title);
            date=itemView.findViewById(R.id.date);
            img_loge=itemView.findViewById(R.id.img_loge);
            relativelayout=itemView.findViewById(R.id.relativelayout);

        }
    }
}
