package com.xiaoshulin.vipbanlv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.bean.MainDataBean;

/**
 * Created by jp on 2018/4/12.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHodler> {

    private ArrayList<MainDataBean> list;
    public MainAdapter(ArrayList<MainDataBean> list) {
           this.list=list;
    }

    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHodler myViewHodler=new MyViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycle_item_layout,parent,false));

        return myViewHodler;
    }

    @Override
    public void onBindViewHolder(final MyViewHodler holder, int position) {
                holder.title.setText(list.get(position).getTitle());
                holder.content.setText(list.get(position).getContent());

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.relativelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.relativelayout, pos);
                }
            });

            holder.relativelayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.relativelayout, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHodler extends RecyclerView.ViewHolder
    {

        private TextView title,content;
        private RelativeLayout relativelayout;

        public MyViewHodler(View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.big_title);
            content=itemView.findViewById(R.id.tv_content);
            relativelayout=itemView.findViewById(R.id.relativelayout);
        }
    }


    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
