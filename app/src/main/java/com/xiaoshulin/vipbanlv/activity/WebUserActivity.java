package com.xiaoshulin.vipbanlv.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPActivity;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.CricleCenterFragment;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.RemarksFragment;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.RemarksFragment1;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.SinpleCricleFragment;
import com.xiaoshulin.vipbanlv.fragment.HomeFragment;
import com.xiaoshulin.vipbanlv.fragment.MessageFragment;
import com.xiaoshulin.vipbanlv.fragment.UserFragment;
import com.xiaoshulin.vipbanlv.presenter.ShareWechatPresenter;
import com.xiaoshulin.vipbanlv.view.IShareWeChatView;

@Route(path = "/activity/WebUserActivity")
public class WebUserActivity extends BaseMVPActivity implements IShareWeChatView {

    @Autowired
    public int tag;
    @Autowired
    public String cricleid;
    @Autowired
    public String produceid;

    private ShareWechatPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_user;
    }

    @Override
    protected void initView() {

    }

    @Override
    public BasePresenter initPresenter() {
        return  presenter = new ShareWechatPresenter(this);
    }

    @Override
    protected void initData() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment fragment=null;
        switch (tag)
        {
            case 0:
                /**个人中心*/
                fragment = UserFragment.newInstance();
                break;
            case 1:
                /**个人圈子查看*/
                fragment=CricleCenterFragment.newInstance(cricleid);
                break;
            case 2:
                /**消息中心*/
                fragment=MessageFragment.newInstance();
                break;
            case 3:
                /**圈子备注*/
                fragment=RemarksFragment.newInstance(cricleid,produceid);
                break;
            case 4:
                fragment=RemarksFragment1.newInstance(cricleid,produceid);
                break;
//            case 5:
//                /**个人中心*/
//                fragment = UserFragment.newInstance();
//                break;
            default:
                Log.e("jjjjjj", "initData:   "+tag);
                break;
        }


        transaction.replace(R.id.user_fragment, fragment);
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
