package com.xiaoshulin.vipbanlv.view;

import com.xiaoshulin.vipbanlv.bean.SupplyAccountInforBean;

/**
 * ISupplyAccountView
 *
 * @author jipeng
 * @date 2018/10/22
 */
public interface ISupplyAccountView {
    void submitAccountSuccess();

    String getpid();

    String getaid();

    String getusername();

    String getpassword();

    String getdays();

    String getwechat();

    void accountInformation(SupplyAccountInforBean bean);
}
