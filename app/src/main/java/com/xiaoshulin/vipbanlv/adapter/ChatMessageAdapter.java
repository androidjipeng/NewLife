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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.database.entity.ChatMessageEntity;
import com.xiaoshulin.vipbanlv.utils.SharePreferenceUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by jipeng on 2018/11/26.
 */
public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatMessageViewhodler> {

    private List<ChatMessageEntity> list;
    Context context;
    public ChatMessageAdapter(Context context,List<ChatMessageEntity> list) {
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public ChatMessageViewhodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_item_layout, parent, false);
        ChatMessageViewhodler chatMessageViewhodler = new ChatMessageViewhodler(view);
        return chatMessageViewhodler;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageViewhodler holder, int position) {
        ChatMessageEntity entity = list.get(position);

        if (entity.getType().equals("1")) {
            //对方 左边
            holder.ll_recive.setVisibility(View.VISIBLE);
            holder.rl_send.setVisibility(View.GONE);
            holder.chat_recive.setText(entity.getText());
            try {
                InputStream is = context.getResources().getAssets().open(list.get(position).getIcon()+".png");
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                holder.img_recive.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (entity.getType().equals("0")) {
            //自己  右边
            holder.ll_recive.setVisibility(View.GONE);
            holder.rl_send.setVisibility(View.VISIBLE);
            holder.chat_send.setText(entity.getText());
            String myIcon = SharePreferenceUtil.getinstance().getMyIcon();
            try {
                InputStream is = context.getResources().getAssets().open(myIcon+".png");
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                holder.img_send.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (entity.getType().equals("888")) {
            //客服  左边
            holder.ll_recive.setVisibility(View.VISIBLE);
            holder.rl_send.setVisibility(View.GONE);
            holder.chat_recive.setText(entity.getText());
            try {
                InputStream is = context.getResources().getAssets().open(list.get(position).getIcon()+".png");
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                holder.img_recive.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        holder.ll_recive.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnItemClickLitener.onItemLeftClick(view,position);
                return false;
            }
        });

        holder.rl_send.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnItemClickLitener.onItemRightClick(view,position);
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ChatMessageViewhodler extends RecyclerView.ViewHolder {
        private LinearLayout ll_recive;
        private RelativeLayout rl_send;
        private ImageView img_send, img_recive;
        private TextView chat_recive, chat_send;

        public ChatMessageViewhodler(View itemView) {
            super(itemView);
            ll_recive = itemView.findViewById(R.id.ll_recive);
            rl_send = itemView.findViewById(R.id.rl_send);
            img_send = itemView.findViewById(R.id.img_send);
            img_recive = itemView.findViewById(R.id.img_recive);
            chat_recive = itemView.findViewById(R.id.chat_recive);
            chat_send = itemView.findViewById(R.id.chat_send);

        }
    }


    public interface OnItemClickLitener
    {
        void onItemLeftClick(View view, int position);
        void onItemRightClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }



}
