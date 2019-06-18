package com.xiaoshulin.vipbanlv.circleFrags.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.MainActivity;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.adapter.ChatMessageAdapter;
import com.xiaoshulin.vipbanlv.bean.CricleBean;
import com.xiaoshulin.vipbanlv.image.ImagePreviewActivity;
import com.xiaoshulin.vipbanlv.utils.MyGridview;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jipeng on 2018/12/20.
 */
public class CircleAdapter1 extends RecyclerView.Adapter<CircleAdapter1.ViewHodler> {
    private static final String TAG = "CircleAdapter1";
    private List<CricleBean.DataBean> list;
    private Context context;
    private int tag;
    public List<CricleBean.DataBean> getList() {
        return list;
    }

    public void setList(List<CricleBean.DataBean> list) {
        this.list = list;
    }

    public CircleAdapter1(Context context, List<CricleBean.DataBean> list,int tag) {
        this.list = list;
        this.context=context;
        this.tag=tag;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cricle_childs_item_layout,parent,false);
        ViewHodler hodler=new ViewHodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        holder.user_name.setText(list.get(position).getName());
        holder.tv_item_content.setText(list.get(position).getContent());
        try {
            InputStream is = context.getResources().getAssets().open(list.get(position).getAvatar()+".png");
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            holder.img_icon.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CriclePicGradviewAdapter adapter=new CriclePicGradviewAdapter(context,list.get(position).getThumbnails());
        holder.gridView.setAdapter(adapter);
        ArrayList<String> images=new ArrayList<>();
        images.addAll(list.get(position).getImgs());
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(context,ImagePreviewActivity.class);
                intent.putStringArrayListExtra("images",images);
                intent.putExtra("position", i);
                context.startActivity(intent);
            }
        });

        holder.tip_content.setText(list.get(position).getHyperlinkdescribe());
        String price = list.get(position).getPrice();
        double p=Double.parseDouble(price)*0.96;

        holder.tv_rmb1.setText("￥"+String.format("%.2f", p));

        holder.tv_rmb2.setText("￥"+list.get(position).getPrice());
        holder.tv_rmb2_content.setText("#"+list.get(position).getAssuredate()+"天担保期#");
        holder.day.setText(list.get(position).getDate());
        if (tag==4){

            holder.describe2.setText("查看订单");
        }

        boolean collect = list.get(position).getIscollect().equals("1");
        if (collect)
        {
            holder.describe1.setText("#取消收藏#");
        }else {
            holder.describe1.setText("#我要收藏#");
        }
         /**收藏按钮*/
        holder.describe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,0);

                if (collect)
                  {
                      list.get(position).setIscollect("0");
                  }else {
                      list.get(position).setIscollect("1");
                  }
                  notifyDataSetChanged();

            }
        });
        /**立即抢购*/
        holder.describe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,1);
            }
        });
        /**客服咨询*/
        holder.describe3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,2);
            }
        });
        /**分享*/
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,3);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,4);
            }
        });

        holder.rewrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,5);
            }
        });

        holder.back_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,6);
            }
        });

        holder.appreciate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,7);
            }
        });
        /**
         * 圈子的头像
         * */
        holder.img_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,8);
            }
        });
        /**用户名点击*/
        holder.user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,8);
            }
        });

        holder.tv_item_content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,9);
                return false;
            }
        });

        /**投诉*/
        holder.complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickLitener.onItemAllClick(view,position,10);
            }
        });

        /**圈子超链描述*/
        holder.ll_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=list.get(position).getHyperlinkurl();
                try {
                    String result = URLDecoder.decode(url, "UTF-8");
//                    ARouter.getInstance()
//                            .build("/activity/WebActivity")
//                            .withString("url",result)
//                            .navigation();
                    ParsingTools tools=new ParsingTools();
                    tools.SecondParseTool(context,result);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        holder.tv_rmb1_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="http://www.vipbanlv.com/#!/sunny-card";
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url",url)
                        .navigation();
            }
        });

        holder.tv_rmb2_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://mp.weixin.qq.com/s/OrP_s4x-_wGITxYyxST02A";
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url",url)
                        .navigation();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder{

        private TextView tv_item_content,user_name,tip_content,tv_rmb1,tv_rmb1_content,tv_rmb2,tv_rmb2_content,describe1,describe2,describe3,day,share,delete,rewrite,back_top,appreciate,complain;
        private MyGridview gridView;
        private ImageView img_icon,img_tip;
        LinearLayout ll_tip;
        public ViewHodler(View itemView) {
            super(itemView);
            tv_item_content=itemView.findViewById(R.id.tv_item_content);
            gridView=itemView.findViewById(R.id.gridView);

            user_name=itemView.findViewById(R.id.user_name);
            tip_content=itemView.findViewById(R.id.tip_content);
            tv_rmb1=itemView.findViewById(R.id.tv_rmb1);
            tv_rmb1_content=itemView.findViewById(R.id.tv_rmb1_content);
            tv_rmb2=itemView.findViewById(R.id.tv_rmb2);
            tv_rmb2_content=itemView.findViewById(R.id.tv_rmb2_content);
            describe1=itemView.findViewById(R.id.describe1);
            describe2=itemView.findViewById(R.id.describe2);
            describe3=itemView.findViewById(R.id.describe3);
            day=itemView.findViewById(R.id.day);
            share=itemView.findViewById(R.id.share);
            delete=itemView.findViewById(R.id.delete);
            rewrite=itemView.findViewById(R.id.rewrite);
            back_top=itemView.findViewById(R.id.back_top);
            appreciate=itemView.findViewById(R.id.appreciate);

            img_icon=itemView.findViewById(R.id.img_icon);
            img_tip=itemView.findViewById(R.id.img_tip);

            ll_tip=itemView.findViewById(R.id.ll_tip);

            complain=itemView.findViewById(R.id.complain);

        }
    }

    public interface OnItemClickLitener
    {

        void onItemAllClick(View view,int position,int tag);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
