package com.xiaoshulin.vipbanlv.circleFrags.view;

import com.xiaoshulin.vipbanlv.bean.CheckVisionBean;
import com.xiaoshulin.vipbanlv.bean.CircleAdvertisementBean;
import com.xiaoshulin.vipbanlv.bean.CricleBean;
import com.xiaoshulin.vipbanlv.bean.CricleBuyPro;
import com.xiaoshulin.vipbanlv.bean.CricleBuyToInterial;
import com.xiaoshulin.vipbanlv.bean.SupportBean;
import com.xiaoshulin.vipbanlv.bean.singleCircleBean;

/**
 * Created by jipeng on 2019/3/29.
 */
public interface ISinpleCircleView {

    void CircleListData(singleCircleBean bean);
    String circlelisttype();

    void CircleAdv(CircleAdvertisementBean bean);

    void collectSuccess(int returncode,String mess);

    void DelectCircleSuccess(int returncode);

    void payToAlipay(CricleBuyPro buyPro);

    void payToIntegral(CricleBuyToInterial integralMessage);

    void getV_money(CheckVisionBean checkVisionBean);

    void payisOk(String newid);

    void enjoy(SupportBean bean);
}
