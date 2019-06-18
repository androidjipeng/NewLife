package com.xiaoshulin.vipbanlv.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;

/**
 * JPushDialog
 *
 * @author jipeng
 * @date 2018/8/8
 */
public class JPushDialog extends Dialog{
    private TextView tv_message,tv_cancel,tv_submit,tv_ID,tv_know;
    private LinearLayout ll;
    public JPushDialog(@NonNull Context context, String pushtyp,String title, String message, View.OnClickListener listener) {
        super(context, R.style.AppTheme_Dialog);
        setContentView(R.layout.main_dialog_item_layout);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        tv_know=findViewById(R.id.tv_know);
        ll=findViewById(R.id.ll);

        tv_ID=findViewById(R.id.tv_ID);
        tv_message=findViewById(R.id.tv_message);
        tv_cancel=findViewById(R.id.tv_cancel);
        tv_submit=findViewById(R.id.tv_submit);

        if (pushtyp.equals("1")||pushtyp.equals("14"))
        {
            tv_know.setVisibility(View.VISIBLE);
            ll.setVisibility(View.GONE);
            if (pushtyp.equals("14")){
                tv_know.setText("去回复");
            }
        }else if (pushtyp.equals("4")){
            tv_cancel.setText("稍后");
            tv_submit.setText("去围观");
        }else if (pushtyp.equals("8")){
            tv_cancel.setText("稍后");
            tv_submit.setText("去看看");
        }else if (pushtyp.equals("15")){
            tv_cancel.setText("稍后");
            tv_submit.setText("免费看");
        }

        tv_ID.setText(title);
        tv_message.setText(message);

        tv_cancel.setOnClickListener(listener);
        tv_submit.setOnClickListener(listener);
        tv_know.setOnClickListener(listener);
//        show();
    }
}
