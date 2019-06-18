package com.xiaoshulin.vipbanlv.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;

/**
 * CheckUpLoadDialog
 *
 * @author jipeng
 * @date 2018/8/16
 */
public class CheckUpLoadDialog extends Dialog{
    private TextView tv_message,tv_cancel,tv_submit,tv_ID;
    public CheckUpLoadDialog(@NonNull Context context, String title, String message, String left,String right,View.OnClickListener listener) {
        super(context, R.style.AppTheme_Dialog);
        setContentView(R.layout.main_dialog_item_layout);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        tv_ID=findViewById(R.id.tv_ID);
        tv_message=findViewById(R.id.tv_message);

        tv_cancel=findViewById(R.id.tv_cancel);
        tv_submit=findViewById(R.id.tv_submit);
        tv_cancel.setText(left);
        tv_submit.setText(right);
        tv_ID.setText(title);
        tv_message.setText(message);
        tv_cancel.setOnClickListener(listener);
        tv_submit.setOnClickListener(listener);
    }
}
