package com.xiaoshulin.vipbanlv.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.xiaoshulin.vipbanlv.R;

/**
 * Created by jipeng on 2019-10-18 16:47.
 */
public class NewHomePop extends PopupWindow {

    public NewHomePop(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.pop_new_home_friend_item_layout,null,false);
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
/*        ColorDrawable dw = new ColorDrawable(-00000);
        mMenuView.setBackgroundDrawable(dw);*/
        this.setAnimationStyle(R.style.popwin_anim_style);
        this.setBackgroundDrawable(new ColorDrawable(002200));
        this.setOutsideTouchable(true);
        this.setFocusable(true);
    }
}
