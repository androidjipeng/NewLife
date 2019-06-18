package com.xiaoshulin.vipbanlv.user.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.bean.VMoneyBean;

import java.util.List;

/**
 * VmoneyAdapter
 *
 * @author jipeng
 * @date 2018/9/28
 */
public class VmoneyAdapter extends RecyclerView.Adapter<VmoneyAdapter.ViewHolder> {
    private Context context;
    private List<VMoneyBean.ListBean> list;

    public VmoneyAdapter(Context context, List<VMoneyBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.income_item_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_key.setText(list.get(position).getName());
        holder.tv_value.setText(list.get(position).getV());
        holder.ll_income.setOnClickListener(new View.OnClickListener() {
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

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_key,tv_value;
        LinearLayout ll_income;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_income=itemView.findViewById(R.id.ll_income);
            tv_key=itemView.findViewById(R.id.tv_key);
            tv_value=itemView.findViewById(R.id.tv_value);
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
