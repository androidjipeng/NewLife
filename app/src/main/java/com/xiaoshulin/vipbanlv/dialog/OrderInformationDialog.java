package com.xiaoshulin.vipbanlv.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaoshulin.vipbanlv.R;

/**
 * OrderInformationDialog
 *
 * @author jipeng
 * @date 2018/8/16
 */
public class OrderInformationDialog extends Dialog {
    private TextView tv_message,tv_ID,tv_know;
    private LinearLayout ll;
    public OrderInformationDialog(@NonNull Context context, String title, String content, View.OnClickListener listener) {
        super(context, R.style.AppTheme_Dialog);
        setContentView(R.layout.main_dialog_item_layout);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        tv_ID=findViewById(R.id.tv_ID);
        tv_message=findViewById(R.id.tv_message);
        tv_know=findViewById(R.id.tv_know);
        ll=findViewById(R.id.ll);
        tv_know.setVisibility(View.VISIBLE);
        ll.setVisibility(View.GONE);

        tv_ID.setText(title);
        tv_message.setText(content);
        tv_know.setText("好的");
        tv_know.setOnClickListener(listener);

    }
}
