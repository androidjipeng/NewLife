package com.xiaoshulin.vipbanlv.user.presenter;

import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.user.view.IDetailsView;

/**
 * Created by jipeng on 2018/10/18.
 */

public class DetailsPresenter extends BasePresenter {

    IDetailsView iDetailsView;

    public DetailsPresenter(IDetailsView iDetailsView) {
        this.iDetailsView = iDetailsView;
    }
}
