package com.xiaoshulin.vipbanlv.base;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;



public class BaseViewHolder extends RecyclerView.ViewHolder {
    protected Activity activity;

    public BaseViewHolder(@NonNull Activity activity, @NonNull View itemView) {
        super(itemView);
        this.activity=activity;

    }

    public void setData(Object obj){

    }
}
