package com.xiaoshulin.vipbanlv.presenter;

import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.view.IUserInformationView;

/**
 * Created by jipeng on 2018/9/24.
 */

public class UserInformationPresenter extends BasePresenter {
    IUserInformationView iUserInformationView;

    public UserInformationPresenter(IUserInformationView iUserInformationView) {
        this.iUserInformationView = iUserInformationView;
    }
}
