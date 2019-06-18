package com.xiaoshulin.vipbanlv.presenter;

import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.view.ICircleView;

/**
 * Created by jipeng on 2018/12/18.
 */
public class CirclePresenter extends BasePresenter {
    ICircleView iCircleView;

    public CirclePresenter(ICircleView iCircleView) {
        this.iCircleView = iCircleView;
    }
}
