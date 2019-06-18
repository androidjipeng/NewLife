package com.xiaoshulin.vipbanlv.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.bean.ShortCutBean;

import java.util.List;

/**
 * Created by jipeng on 2018/11/29.
 */
public class ShortCutKeyAdapter extends RecyclerView.Adapter<ShortCutKeyAdapter.ViewHolder> {


    protected List<ShortCutBean> list;

    public ShortCutKeyAdapter(List<ShortCutBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shortkey_item_layout, parent, false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShortCutBean shortCutBean = list.get(position);
        holder.tv1.setText(shortCutBean.getTitle1());
        holder.tv2.setText(shortCutBean.getTitle2());
        holder.ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemTopClick(view,position);
            }
        });

        holder.ll_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemBottomClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2;
        LinearLayout ll_top,ll_bottom;
        public ViewHolder(View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.tv_title1);
            tv2=itemView.findViewById(R.id.tv_title2);
            ll_top=itemView.findViewById(R.id.ll_top);
            ll_bottom=itemView.findViewById(R.id.ll_bottom);
        }
    }



    public interface OnItemClickLitener
    {
        void onItemTopClick(View view, int position);
        void onItemBottomClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
