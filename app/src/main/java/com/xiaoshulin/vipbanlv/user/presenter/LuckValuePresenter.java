package com.xiaoshulin.vipbanlv.user.presenter;

import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.user.view.ILuckValueView;

/**
 * LuckValuePresenter
 *
 * @author jipeng
 * @date 2018/9/30
 */
public class LuckValuePresenter extends BasePresenter {
    private ILuckValueView iLuckValueView;

    public LuckValuePresenter(ILuckValueView iLuckValueView) {
        this.iLuckValueView = iLuckValueView;
    }
}
