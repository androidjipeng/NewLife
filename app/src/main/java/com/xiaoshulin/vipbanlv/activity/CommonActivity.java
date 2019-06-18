package com.xiaoshulin.vipbanlv.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.CricleCenterFragment;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.RemarksFragment;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.SinpleCricleFragment;
import com.xiaoshulin.vipbanlv.fragment.MessageFragment;
import com.xiaoshulin.vipbanlv.fragment.UserFragment;
import com.xiaoshulin.vipbanlv.presenter.CommonPresenter;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;
import com.xiaoshulin.vipbanlv.view.ICommentView;


/**
 * 待使用，用于frag的容器
 */
@Route(path = "/activity/CommonActivity")
public class CommonActivity extends BaseMVPActivity implements ICommentView {

    @Autowired
    public int tag;
    @Autowired
    public String cricleid;
    @Autowired
    public String produceid;
    private CommonPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common;
    }

    @Override
    protected void initView() {

    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new CommonPresenter(this);
    }

    @Override
    protected void initData() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment = null;
        switch (tag) {
            case 0:
                /**单个圈子*/
                /**查看单个圈子*/
                fragment = SinpleCricleFragment.newInstance(cricleid);
                break;
            case 1:
                /*为了处理备注信息*/
                /**个人中心*/
                fragment = UserFragment.newInstance();
                break;
            case 2:
                /**圈子备注*/
                fragment = RemarksFragment.newInstance(cricleid, produceid);
                break;
            default:
                break;
        }
        transaction.replace(R.id.common_fragment, fragment);
        transaction.commit();

    }

    @Override
    public void showNullLayout() {

    }

    @Override
    public void hideNullLayout() {

    }

    @Override
    public void showErrorLayout(View.OnClickListener listener) {

    }

    @Override
    public void hideErrorLayout() {

    }
}
