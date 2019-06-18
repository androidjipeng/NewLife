package com.xiaoshulin.vipbanlv.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;

/**
 * MainDialog
 *
 * @author jipeng
 * @date 2018/7/27
 */
public class MainDialog extends Dialog{

    private TextView tv_message,tv_cancel,tv_submit,tv_ID;
    public MainDialog(@NonNull Context context, String ID,String message, View.OnClickListener listener) {
        super(context, R.style.AppTheme_Dialog);
        setContentView(R.layout.main_dialog_item_layout);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        tv_ID=findViewById(R.id.tv_ID);
        tv_message=findViewById(R.id.tv_message);
        tv_cancel=findViewById(R.id.tv_cancel);
        tv_submit=findViewById(R.id.tv_submit);
        tv_ID.setText("用户ID:"+ID+"免费分享");
        tv_message.setText(message);
        tv_cancel.setOnClickListener(listener);
        tv_submit.setOnClickListener(listener);



    }
}
