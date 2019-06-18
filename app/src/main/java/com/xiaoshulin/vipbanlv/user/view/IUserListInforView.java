package com.xiaoshulin.vipbanlv.user.view;

import com.xiaoshulin.vipbanlv.bean.InComeBean;
import com.xiaoshulin.vipbanlv.bean.InformationBean;
import com.xiaoshulin.vipbanlv.bean.SupplyAccountBean;
import com.xiaoshulin.vipbanlv.bean.TryOutBean;
import com.xiaoshulin.vipbanlv.bean.VLevelBean;
import com.xiaoshulin.vipbanlv.bean.VMoneyBean;

/**
 * Created by jipeng on 2018/9/24.
 */

public interface IUserListInforView {
    void getInComeData(InComeBean bean);
    void getVLevelData(VLevelBean bean);
    void getVmoneyData(VMoneyBean bean);
    void getTryOutData(TryOutBean bean);
    void getInformation(InformationBean bean);
    void getSupplyAccountlist(SupplyAccountBean bean);
}
