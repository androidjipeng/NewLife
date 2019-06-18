package com.xiaoshulin.vipbanlv.user.view;

import com.xiaoshulin.vipbanlv.bean.UserAccountListBean;

/**
 * Created by jipeng on 2018/10/10.
 */

public interface IUserAccountListView {
    void getAccountListData(UserAccountListBean bean);

    String getpid();
}
