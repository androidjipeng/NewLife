package com.xiaoshulin.vipbanlv.view;

import android.view.View;

import java.util.ArrayList;

import com.xiaoshulin.vipbanlv.base.BaseView;
import com.xiaoshulin.vipbanlv.bean.CheckVisionBean;
import com.xiaoshulin.vipbanlv.bean.MainDataBean;
import com.xiaoshulin.vipbanlv.bean.RegisterBean;
import com.xiaoshulin.vipbanlv.bean.ShareCircleBean;
import com.xiaoshulin.vipbanlv.bean.SupportBean;
import com.xiaoshulin.vipbanlv.bean.danMuBean;

/**
 * Created by jp on 2018/4/12.
 */

public interface MainView extends BaseView {


    String geturl();

    String getid();

    String getshareid();

    String getUID();

    void getVideoUrl(String url);

    void showOldMessage(String mess);

    void sharecircle(ShareCircleBean url);

    void getRegisterdata(RegisterBean registerBean);

    void getDanmuData(danMuBean bean);

    void getSupportMoney(SupportBean registerBean);

    void getCheckVisionBean(CheckVisionBean bean);
}
