package com.xiaoshulin.vipbanlv.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected Activity mActivity;
    protected Context mContext;
    public ProgressDialog loadingProgressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            return mRootView;
        }

        mRootView = inflater.inflate(getLayoutId(), container, false);
        initView();
        initMvpPresenter();
        initData();
        addListener();
        return mRootView;
    }


    protected void addListener() {

    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected void initMvpPresenter() {

    }

    public ProgressDialog showProgressDialog(String message) {
        return showProgressDialog("", message, false);
    }

    /**
     * 打开加载框,用于调用接口弹出的对话框
     *
     * @param title
     * @param message
     * @return
     */
    public ProgressDialog showProgressDialog(String title, String message) {
        return showProgressDialog(title, message, false);
    }


    /**
     * 打开加载框,用于调用接口弹出的对话框,并设置点击外部是否消失
     *
     * @param title
     * @param message
     * @return
     */
    public ProgressDialog showProgressDialog(String title, String message, final boolean isDismissOut) {
        try {
            if (loadingProgressDialog == null) {
                loadingProgressDialog = ProgressDialog.show(mContext, title,
                        message);
            } else {
                if (!TextUtils.isEmpty(title)) {
                    loadingProgressDialog.setTitle(title);
                }
                loadingProgressDialog.setMessage(message);
                if (!loadingProgressDialog.isShowing()) {
                    loadingProgressDialog.show();
                }
            }
            loadingProgressDialog.setCancelable(isDismissOut);
            loadingProgressDialog.setCanceledOnTouchOutside(isDismissOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadingProgressDialog;
    }

    /**
     * 关闭对话框
     */
    public void dismissProgressDialog() {
        try {
            if (loadingProgressDialog != null) {
                loadingProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
