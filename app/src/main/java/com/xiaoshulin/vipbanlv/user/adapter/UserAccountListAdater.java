package com.xiaoshulin.vipbanlv.user.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.bean.UserAccountListBean;

import java.util.List;

/**
 * Created by jipeng on 2018/10/10.
 */

public class UserAccountListAdater extends RecyclerView.Adapter<UserAccountListAdater.ViewHolder> {

    private Context context;
    private List<UserAccountListBean.ListBean> list;

    public UserAccountListAdater(Context context, List<UserAccountListBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.try_out_item_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getImg())
                .into(holder.try_img);
        holder.try_name.setText(list.get(position).getName());
        holder.try_num.setText(list.get(position).getEndtime());
        holder.linLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView try_img;
        TextView try_num, try_name;
        LinearLayout linLayout4;
        public ViewHolder(View itemView) {
            super(itemView);
            try_img = itemView.findViewById(R.id.try_img);
            try_num = itemView.findViewById(R.id.try_num);
            try_name = itemView.findViewById(R.id.try_name);
            linLayout4 = itemView.findViewById(R.id.linLayout4);
        }
    }

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
