package com.xiaoshulin.vipbanlv.popup;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;

/**
 * JpushPopupwindow
 *
 * @author jipeng
 * @date 2018/8/17
 */
public class JpushPopupwindow extends PopupWindow {
    private static final String TAG = "JpushPopupwindow";
    TextView title, content;
    View view;

    public JpushPopupwindow(Context context, String title, String content) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.jpush_notification_layout, null, true);
        this.title = view.findViewById(R.id.title);
        this.content = view.findViewById(R.id.content);
        this.title.setText(title);
        this.content.setText(content);
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

        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e(TAG, "onTick: " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.e(TAG, "onFinish:      倒计时结束");
                dismiss();
            }
        };
        countDownTimer.start();

    }
}
