package com.xiaoshulin.vipbanlv.base;

import android.view.View;

/**
 * Created by zeda on 2017/12/8.
 */
public interface BaseView {
    /**
     * 显示空数据布局
     */
    void showNullLayout();

    /**
     * 隐藏空数据布局
     */
    void hideNullLayout();

    /**
     * 显示异常布局
     * @param listener
     */
    void showErrorLayout(View.OnClickListener listener);
    /**
     * 隐藏异常布局
     */
    void hideErrorLayout();


}
