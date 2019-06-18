package com.xiaoshulin.vipbanlv.circleFrags.view;

import com.xiaoshulin.vipbanlv.bean.CheckVisionBean;
import com.xiaoshulin.vipbanlv.bean.CircleAdvertisementBean;
import com.xiaoshulin.vipbanlv.bean.CricleBean;
import com.xiaoshulin.vipbanlv.bean.CricleBuyPro;
import com.xiaoshulin.vipbanlv.bean.CricleBuyToInterial;

/**
 * Created by jipeng on 2018/12/20.
 */
public interface IAllCircleView {

 String circlelisttype();

 String page();

 void CircleListData(CricleBean bean);

 void LoadmoreCirlceListData(CricleBean bean);

 void CircleAdv(CircleAdvertisementBean bean);

 void collectSuccess(int returncode,String mess);

 void DelectCircleSuccess(int returncode);

 void payToAlipay(CricleBuyPro buyPro);

 void payToIntegral(CricleBuyToInterial integralMessage);

 void getV_money(CheckVisionBean checkVisionBean);

 void payisOk(String newid);
}
