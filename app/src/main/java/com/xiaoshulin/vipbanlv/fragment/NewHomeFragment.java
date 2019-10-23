package com.xiaoshulin.vipbanlv.fragment;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.NewHomeFragmentBean;
import com.xiaoshulin.vipbanlv.NewHomefrags.adapter.NewHomeFragmentAdapter;
import com.xiaoshulin.vipbanlv.bean.result.NewHomeBottomWelfareBean;
import com.xiaoshulin.vipbanlv.bean.result.NewHomeTopAdvBean;
import com.xiaoshulin.vipbanlv.bean.result.NewHomebottomFriendsBean;
import com.xiaoshulin.vipbanlv.popup.NewHomePop;
import com.xiaoshulin.vipbanlv.popup.NewHomeRightPop;
import com.xiaoshulin.vipbanlv.presenter.NewHomeFragmentPresenter;
import com.xiaoshulin.vipbanlv.utils.ParsingTools;
import com.xiaoshulin.vipbanlv.view.INewHomeFragmentView;

import java.util.List;

/**
 * 新的首页UI页面
 */
public class NewHomeFragment extends BaseMVPFragment implements INewHomeFragmentView, View.OnClickListener {

    NewHomeFragmentPresenter presenter;

    XRecyclerView home_new_recycle;

    NewHomeFragmentAdapter newHomeFragmentAdapter;


    private ViewFlipper view_flipper;
    TextView home_friends, home_welfare_agency;
    Button btn_home_left, btn_home_right;

    RelativeLayout rl_home;

    public static NewHomeFragment newInstance() {
        NewHomeFragment fragment = new NewHomeFragment();
        return fragment;
    }

    public NewHomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initData() {

        //获取广告
        presenter.getAdvHomeData();

        //获取首页数据
        presenter.getHomeData();

    }

    @Override
    protected void initView() {
        home_new_recycle = mRootView.findViewById(R.id.home_new_recycle);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        home_new_recycle.setLayoutManager(layoutManager);
        newHomeFragmentAdapter = new NewHomeFragmentAdapter(getActivity());
        home_new_recycle.setAdapter(newHomeFragmentAdapter);

        view_flipper = (ViewFlipper) mRootView.findViewById(R.id.view_flipper);

        btn_home_left = mRootView.findViewById(R.id.btn_home_left);
        btn_home_right = mRootView.findViewById(R.id.btn_home_right);

        home_friends = mRootView.findViewById(R.id.home_friends);
        home_welfare_agency = mRootView.findViewById(R.id.home_welfare_agency);

        btn_home_left.setOnClickListener(this);
        btn_home_right.setOnClickListener(this);
        home_friends.setOnClickListener(this);
        home_welfare_agency.setOnClickListener(this);

        rl_home = mRootView.findViewById(R.id.rl_home);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_home;
    }

    @Override
    public BasePresenter initPresenter() {
        presenter = new NewHomeFragmentPresenter(this);
        return presenter;
    }

    @Override
    public void getNewHomeFragmentData(NewHomeFragmentBean bean) {
        //首页接口数据返回

        showHomeUI(bean);

    }

    @Override
    public void getNewHomeFragmentAdvData(NewHomeTopAdvBean homeTopAdvBean) {
        //首页接口广告位数据返回

        showNewHomeAdvUI(homeTopAdvBean);

    }

    @Override
    public void getNewHomeBottomWelfareData(NewHomeBottomWelfareBean newHomeBottomWelfareBean) {
        //福利社的数据返回   
        
        showBottomWelfarePop(newHomeBottomWelfareBean);
        
    }

  

    @Override
    public void getNewHomebottomFriendsData(NewHomebottomFriendsBean newHomebottomFriendsBean) {
        //密友圈的数据返回

        showBottomFriendsPop(newHomebottomFriendsBean);
        
    }

    private void showBottomFriendsPop(NewHomebottomFriendsBean newHomebottomFriendsBean) {
        NewHomePop pop = new NewHomePop(getContext(),newHomebottomFriendsBean);
        int offsetX = (rl_home.getMeasuredWidth() + 400);
        int offsetY = -(home_friends.getMeasuredHeight() + home_friends.getHeight() * 4);

        if (Build.VERSION.SDK_INT < 24) {
            pop.showAsDropDown(home_friends);
//            popupWindow.showAsDropDown(anchor);
        } else {
            // 适配 android 7.0
            int[] location = new int[2];
            // 获取控件在屏幕的位置
            rl_home.getLocationOnScreen(location);
            pop.showAtLocation(rl_home, Gravity.NO_GRAVITY, 0, location[1]+rl_home.getHeight()+offsetY);

        }
//        pop.showAsDropDown(rl_home, 0, offsetY, Gravity.BOTTOM);
//        pop.update();
    }

    private void showBottomWelfarePop(NewHomeBottomWelfareBean newHomeBottomWelfareBean) {

        NewHomeRightPop pop1 = new NewHomeRightPop(getContext(),newHomeBottomWelfareBean);
        int offsetY1 = -(home_welfare_agency.getMeasuredHeight() + home_welfare_agency.getHeight() * 4);
        if (Build.VERSION.SDK_INT < 24) {
            pop1.showAsDropDown(home_welfare_agency);
//            popupWindow.showAsDropDown(anchor);
        } else {
            // 适配 android 7.0
            int[] location = new int[2];
            // 获取控件在屏幕的位置
            rl_home.getLocationOnScreen(location);
            pop1.showAtLocation(rl_home, Gravity.NO_GRAVITY, 0, location[1] +rl_home.getHeight()+offsetY1);

        }
//        pop1.showAsDropDown(rl_home, 0, offsetY1, Gravity.BOTTOM);
//        pop1.update();
    }

    private void showNewHomeAdvUI(NewHomeTopAdvBean homeTopAdvBean) {
        List<NewHomeTopAdvBean.ListBean> advList = homeTopAdvBean.getList();
        if (advList != null && advList.size() > 0) {
            for (int i = 0; i < advList.size(); i++) {
                View view = getLayoutInflater().inflate(R.layout.new_home_adv_item_layout, null);
                TextView tv_adv_content = view.findViewById(R.id.tv_adv_content);
                tv_adv_content.setText(advList.get(i).getDescribe());
                int pos = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = advList.get(pos).getUrl();
                        ParsingTools tools = new ParsingTools();
                        tools.SecondParseTool(getContext(), url);

                        Log.e("jp", "广告的url------->>>url:" + url);
                    }
                });
                view_flipper.addView(view);
            }

            view_flipper.setFlipInterval(2000);
            view_flipper.startFlipping();
        }

    }


    //组装首页的四个item布局
    private void showHomeUI(NewHomeFragmentBean bean) {
        List<NewHomeFragmentBean> list = newHomeFragmentAdapter.getList();

        NewHomeFragmentBean firstbean = new NewHomeFragmentBean();
        firstbean.setToplist(bean.getToplist());
        firstbean.setToplisttime(bean.getToplisttime());
        list.add(firstbean);

        NewHomeFragmentBean secondbean = new NewHomeFragmentBean();
        secondbean.setNewsdata(bean.getNewsdata());
        secondbean.setNewsdatatime(bean.getNewsdatatime());
        list.add(secondbean);

        NewHomeFragmentBean thirdbean = new NewHomeFragmentBean();
        thirdbean.setGeneralizelist(bean.getGeneralizelist());
        thirdbean.setGeneralizelisttime(bean.getGeneralizelisttime());
        list.add(thirdbean);

        NewHomeFragmentBean fourthbean = new NewHomeFragmentBean();
        fourthbean.setVlivelist(bean.getVlivelist());
        fourthbean.setVlivelisttime(bean.getVlivelisttime());
        list.add(fourthbean);

        newHomeFragmentAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home_left:
                //今日头条
                String url="https://m.toutiao.com/?W2atIF=1";
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url", url)
                        .navigation();
                break;
            case R.id.btn_home_right:
                //树林生活
                String url_xiaoshulin="http://www.vipbanlv.com/v2_test/#!/life";
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url", url_xiaoshulin)
                        .navigation();
                break;
            case R.id.home_friends:
                //密友圈
                presenter.getFriendsData();

                break;
            case R.id.home_welfare_agency:
                //福利社
                presenter.getWelfareData();


                break;
            default:


        }
    }
}
