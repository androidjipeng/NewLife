package com.xiaoshulin.vipbanlv.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;

/**
 * Created by jipeng on 2018/9/1.
 */

public class PremissionsDialog extends Dialog {
    TextView tv_ok;
    TextView tv_mess;
    public PremissionsDialog(@NonNull Context context,String message, View.OnClickListener listener) {
        super(context, R.style.AppTheme_Dialog);
        setContentView(R.layout.premissions_dialog_layout);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        tv_mess=findViewById(R.id.tv_mess);
        tv_mess.setText(message);
        tv_ok=findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(listener);

    }

    public PremissionsDialog(@NonNull Context context,String message,String btnName, View.OnClickListener listener) {
        super(context, R.style.AppTheme_Dialog);
        setContentView(R.layout.premissions_dialog_layout);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        tv_mess=findViewById(R.id.tv_mess);
        tv_mess.setText(message);
        tv_ok=findViewById(R.id.tv_ok);
        tv_ok.setText(btnName);
        tv_ok.setOnClickListener(listener);

    }



}
