package com.xiaoshulin.vipbanlv.user.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import com.google.gson.Gson;
import com.xiaoshulin.vipbanlv.ApiUtils.ApiUtils;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.bean.InformationBean;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;
import com.xiaoshulin.vipbanlv.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by jipeng on 2018/10/18.
 */

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    private static final String TAG = "InformationAdapter";
    private Context context;
    private List<InformationBean.ListBean> list;

    public InformationAdapter(Context context, List<InformationBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_information_item_layout,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                  holder.infor_mess.setText(list.get(position).getMessage());
                  holder.infor_time.setText(list.get(position).getTime());
                  if (list.get(position).getStatus().equals("0"))
                  {
                      holder.infor_img.setImageResource(R.drawable.red_right);
                  }else {
                      holder.infor_img.setImageResource(R.drawable.right_arrow);
                  }
                  holder.ll_infor.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {

                          list.get(position).setStatus("1");

                          String stringUId = SharePreferenceUtil.getinstance().getStringUId();
                          String stringUIdToken = SharePreferenceUtil.getinstance().getStringUIdToken();
                          String sign= Utils.getUidSign(stringUId);
                          OkHttpUtils.get()
                                  .url(ApiUtils.URL)
                                  .addParams("m","user")
                                  .addParams("a","msgdetail")
                                  .addParams("uid", stringUId)
                                  .addParams("machine_type",String.valueOf(1))
                                  .addParams("sign",sign)
                                  .addParams("version", Utils.getLocalVersionName())
                                  .addParams("uidtoken",stringUIdToken)
                                  .addParams("messageid",list.get(position).getId())
                                  .build()
                                  .execute(new StringCallback() {
                                      @Override
                                      public void onError(Call call, Exception e, int id) {
                                          Log.e(TAG, "onError: "+e.getMessage() );
                                      }

                                      @Override
                                      public void onResponse(String response, int id) {
                                          Log.e(TAG, "onResponse: "+response);
                                          ARouter.getInstance()
                                                  .build("/user/DetailsActivity")
                                                  .withInt("tag",3)
                                                  .withString("message", list.get(position).getMessage())
                                                  .navigation();
                                      }
                                  });

                          notifyDataSetChanged();
                      }
                  });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {

        private TextView infor_mess,infor_time;
        private ImageView infor_img;
        LinearLayout ll_infor;
        public ViewHolder(View itemView) {
            super(itemView);
            ll_infor=itemView.findViewById(R.id.ll_infor);
            infor_mess=itemView.findViewById(R.id.infor_mess);
            infor_time=itemView.findViewById(R.id.infor_time);
            infor_img=itemView.findViewById(R.id.infor_img);
        }
    }
}
