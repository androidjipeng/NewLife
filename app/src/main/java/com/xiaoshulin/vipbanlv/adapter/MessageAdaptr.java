package com.xiaoshulin.vipbanlv.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.database.entity.MessageListEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jipeng on 2018/11/23.
 */
public class MessageAdaptr extends RecyclerView.Adapter<MessageAdaptr.MessageViewHodler>{

    List<MessageListEntity> list;
    Context context;
    public MessageAdaptr(Context context,List<MessageListEntity> list) {
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public MessageViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_message_layout,parent,false);
         MessageViewHodler hodler=new MessageViewHodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHodler holder, int position) {
        String messageSection = list.get(position).getMessageSection();
        if (messageSection.equals("1374"))
        {
            holder.tv_mess_tite.setText("客服小哥");
        }else if (messageSection.equals("1377"))
        {
            holder.tv_mess_tite.setText("客服小妹");
        }else {

            holder.tv_mess_tite.setText(list.get(position).getNickName());
        }
              holder.tv_mess_content.setText(list.get(position).getNewestMessage());
              holder.date.setText(list.get(position).getNewestTime());
        try {
            InputStream is = context.getResources().getAssets().open(list.get(position).getIcon()+".png");
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            holder.img_mess.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
              holder.rl_mess.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      ARouter.getInstance()
                              .build("/activity/ChatMessageActivity")
                              .withString("titleName",list.get(position).getNickName())
                              .withString("messageSectionid",list.get(position).getMessageSection())
                              .navigation();
                  }
              });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MessageViewHodler extends RecyclerView.ViewHolder{
        ImageView img_mess;
        private TextView date,tv_mess_tite,tv_mess_content;
        RelativeLayout rl_mess;
        public MessageViewHodler(View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.mess);
            tv_mess_tite=itemView.findViewById(R.id.tv_mess_tite);
            tv_mess_content=itemView.findViewById(R.id.tv_mess_content);
            img_mess=itemView.findViewById(R.id.img_mess);
            rl_mess=itemView.findViewById(R.id.rl_mess);
        }
    }
}
