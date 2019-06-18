package com.xiaoshulin.vipbanlv.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by jp on 2018/4/12.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    public ProgressDialog loadingProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getLayoutId() != 0){
            setContentView(getLayoutId());
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();
        addListener();
    }
    protected abstract int getLayoutId();

    protected abstract void initView();

    protected void addListener() {

    }

    /**
     * 打开加载框,用于调用接口弹出的对话框
     *
     * @param title
     * @param message
     * @return
     */
    public ProgressDialog showProgressDialog(String title, String message) {
        return showProgressDialog(title,message,false);
    }


    /**
     * 打开加载框,用于调用接口弹出的对话框,并设置点击外部是否消失
     *
     * @param title
     * @param message
     * @return
     */
    public ProgressDialog showProgressDialog(String title, String message,final boolean isDismissOut) {
        try {
            if (!isFinishing()) {
                if (loadingProgressDialog == null) {
                    loadingProgressDialog = ProgressDialog.show(this, title,
                            message);
                } else {
                    loadingProgressDialog.setTitle(title);
                    loadingProgressDialog.setMessage(message);
                    if (!loadingProgressDialog.isShowing()) {
                        loadingProgressDialog.show();
                    }
                }
                loadingProgressDialog.setCancelable(isDismissOut);
                loadingProgressDialog.setCanceledOnTouchOutside(isDismissOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadingProgressDialog;
    }

    /**
     * 关闭对话框
     */
    public void dissmissProgressDialog() {
        try {
            //isFinishing()为了防止正在finish当前页面时dismiss或show   dialog
            if (!isFinishing() && loadingProgressDialog != null) {
                loadingProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
