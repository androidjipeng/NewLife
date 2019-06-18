package com.xiaoshulin.vipbanlv.presenter;

import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.view.ICommentView;

/**
 * Created by jipeng on 2019/1/8.
 */
public class CommonPresenter extends BasePresenter {
    private ICommentView iCommentView;

    public CommonPresenter(ICommentView iCommentView) {
        this.iCommentView = iCommentView;
    }
}
