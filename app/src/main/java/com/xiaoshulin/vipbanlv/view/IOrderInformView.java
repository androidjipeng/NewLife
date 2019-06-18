package com.xiaoshulin.vipbanlv.view;

import com.xiaoshulin.vipbanlv.bean.JPushBean;

/**
 * Created by jipeng on 2018/5/29.
 */

public interface IOrderInformView {
    String getcontent();
    String getpushuid();
    String getpushuid1();
    void show(JPushBean bean);
}
