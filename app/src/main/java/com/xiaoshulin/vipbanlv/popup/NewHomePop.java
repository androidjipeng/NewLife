package com.xiaoshulin.vipbanlv.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.bean.result.NewHomebottomFriendsBean;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;

import java.util.List;

/**
 * Created by jipeng on 2019-10-18 16:47.
 */
public class NewHomePop extends PopupWindow {

    Context context;
    NewHomebottomFriendsBean newHomebottomFriendsBean;
    LinearLayout ll_content;

    public NewHomePop(Context context, NewHomebottomFriendsBean newHomebottomFriendsBean) {
        super(context);
        this.context = context;
        this.newHomebottomFriendsBean = newHomebottomFriendsBean;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_new_home_friend_item_layout, null, false);
        initShowUI(view);
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
/*        ColorDrawable dw = new ColorDrawable(-00000);
        mMenuView.setBackgroundDrawable(dw);*/
        this.setAnimationStyle(R.style.new_home_pop_anim_style);
        this.setBackgroundDrawable(new ColorDrawable(002200));
        this.setOutsideTouchable(true);
        this.setFocusable(true);


    }

    private void initShowUI(View view) {
        ll_content = view.findViewById(R.id.ll_content);

        List<NewHomebottomFriendsBean.BottomlistBean> bottomlist = newHomebottomFriendsBean.getBottomlist();
        if (bottomlist != null && bottomlist.size() > 0) {
            NewHomebottomFriendsBean.BottomlistBean bottomlistBean = bottomlist.get(0);
            List<NewHomebottomFriendsBean.BottomlistBean.ListBean> list = bottomlistBean.getList();
            for (int i = 0; i < list.size(); i++) {
                View itemview = LayoutInflater.from(context).inflate(R.layout.pop_new_home_item_item_layout, null, false);
                TextView textView = itemview.findViewById(R.id.tv_pop_content_item);
                textView.setText(list.get(i).getTitle());
                int pos=i;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ParsingTools tools=new ParsingTools();
                        tools.SecondParseTool(context,list.get(pos).getUrl());
                        dismiss();
                    }
                });
                ll_content.addView(itemview);
            }
        }

    }
}
