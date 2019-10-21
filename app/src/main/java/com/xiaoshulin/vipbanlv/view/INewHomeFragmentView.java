package com.xiaoshulin.vipbanlv.view;

import com.xiaoshulin.vipbanlv.bean.NewHomeFragmentBean;
import com.xiaoshulin.vipbanlv.bean.result.NewHomeBottomWelfareBean;
import com.xiaoshulin.vipbanlv.bean.result.NewHomeTopAdvBean;
import com.xiaoshulin.vipbanlv.bean.result.NewHomebottomFriendsBean;

/**
 * Created by jipeng on 2019-10-15 10:47.
 */
public interface INewHomeFragmentView {

  void getNewHomeFragmentData(NewHomeFragmentBean bean);

  void getNewHomeFragmentAdvData(NewHomeTopAdvBean homeTopAdvBean);

  void getNewHomeBottomWelfareData(NewHomeBottomWelfareBean newHomeBottomWelfareBean);

  void getNewHomebottomFriendsData(NewHomebottomFriendsBean newHomebottomFriendsBean);

}
