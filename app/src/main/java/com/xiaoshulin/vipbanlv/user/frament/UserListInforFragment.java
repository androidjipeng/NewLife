package com.xiaoshulin.vipbanlv.user.frament;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaoshulin.vipbanlv.R;
import com.xiaoshulin.vipbanlv.base.BaseMVPFragment;
import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.InComeBean;
import com.xiaoshulin.vipbanlv.bean.InformationBean;
import com.xiaoshulin.vipbanlv.bean.SupplyAccountBean;
import com.xiaoshulin.vipbanlv.bean.TryOutBean;
import com.xiaoshulin.vipbanlv.bean.VLevelBean;
import com.xiaoshulin.vipbanlv.bean.VMoneyBean;
import com.xiaoshulin.vipbanlv.user.adapter.IncomeAdapter;
import com.xiaoshulin.vipbanlv.user.adapter.InformationAdapter;
import com.xiaoshulin.vipbanlv.user.adapter.SupplyAccountAdapter;
import com.xiaoshulin.vipbanlv.user.adapter.TryOutAdapter;
import com.xiaoshulin.vipbanlv.user.adapter.VLevelAdapter;
import com.xiaoshulin.vipbanlv.user.adapter.VmoneyAdapter;
import com.xiaoshulin.vipbanlv.user.presenter.UserListInforPresenter;
import com.xiaoshulin.vipbanlv.user.view.IUserListInforView;
import com.xiaoshulin.vipbanlv.utils.TitleBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserListInforFragment extends BaseMVPFragment implements IUserListInforView {

    UserListInforPresenter presenter;

    private XRecyclerView common_recycle;

    private TextView titleBar;
    private Button btn1,btn2;
    public UserListInforFragment() {
        // Required empty public constructor
    }

    private int tag;

    @SuppressLint("ValidFragment")
    public UserListInforFragment(int tag) {
        this.tag = tag;
    }

    @Override
    protected void initData() {
        switch (tag) {
            case 2:
                titleBar.setText("收入");
                presenter.IncomeList();
                break;
            case 3:
                titleBar.setText("V币");
                presenter.VmoneyData();
                break;
            case 4:
                titleBar.setText("V级");
                presenter.VLevelData();
                break;
            case 7:
                titleBar.setText("试用账号管理");
                presenter.tryOutData();
                break;
            case 9:
                titleBar.setText("消息");
                presenter.information();
                break;
            case 13:
                titleBar.setText("提供试用");
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
                presenter.SupplyAccountList();
                break;


        }
    }


    @Override
    protected void initView() {
        common_recycle = mRootView.findViewById(R.id.common_recycle);
        titleBar = mRootView.findViewById(R.id.titlebar);

        btn1=mRootView.findViewById(R.id.btn1);//出租教程

        btn2=mRootView.findViewById(R.id.btn2);//优惠充值


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //https://mp.weixin.qq.com/s/vMiSUvtdmX1ap61ZxNzV9w
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url","https://mp.weixin.qq.com/s/vMiSUvtdmX1ap61ZxNzV9w")
                        .navigation();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //https://mp.weixin.qq.com/s/21jo3IOk-pgHPc3LldKYTQ
                ARouter.getInstance()
                        .build("/activity/WebActivity")
                        .withString("url", "https://mp.weixin.qq.com/s/21jo3IOk-pgHPc3LldKYTQ")
                        .navigation();

            }
        });


        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        common_recycle.setLayoutManager(manager);

        common_recycle.setPullRefreshEnabled(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_list_infor;
    }

    @Override
    public BasePresenter initPresenter() {
        return presenter = new UserListInforPresenter(this);
    }

    @Override
    public void getInComeData(InComeBean bean) {
        /**
         * 收入列表数据
         * */
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.user_recycle_common_layout, null);
        TextView key = view.findViewById(R.id.top_key);
        TextView value = view.findViewById(R.id.top_value);
        LinearLayout ll_common=view.findViewById(R.id.ll_common);
        ll_common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build("/user/DetailsActivity")
                        .withInt("tag",0)
                        .withString("message", "截止到目前您的账号在我们平台赚取收益的总和（除去提现），您的每一份收入都由我们记录！")
                        .navigation();
            }
        });
        key.setText("账号试用总收入");
        value.setText(bean.getMoney() + "元");
        common_recycle.addHeaderView(view);

        IncomeAdapter adapter = new IncomeAdapter(getActivity(), bean.getList());
        common_recycle.setAdapter(adapter);
        adapter.setOnItemClickLitener(new IncomeAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ARouter.getInstance()
                        .build("/user/DetailsActivity")
                        .withInt("tag",4)
                        .withString("message", bean.getList().get(position).getName())
                        .withString("titleinfo","收入")
                        .navigation();
            }
        });
    }

    @Override
    public void getVLevelData(VLevelBean bean) {
        /**
         * V级
         * */
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.user_recycle_common_layout, null);
        TextView key = view.findViewById(R.id.top_key);
        TextView value = view.findViewById(R.id.top_value);
        LinearLayout ll_common=view.findViewById(R.id.ll_common);
        ll_common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build("/user/DetailsActivity")
                        .withInt("tag",2)
                        .withString("message", "V级是《小树林》APP的用户信用等级，信用越高者V级越高，《小树林》APP旨在创建一个可信赖的共享王国，里面会有你想要的东西，你也可以提供他人想要的东西，未来一定是一个以信用为基础的社会，我们也一直认为信任是一切的基础。")
                        .navigation();
            }
        });
        key.setText("总V级");
        value.setText(bean.getVcount() + "V");
        common_recycle.addHeaderView(view);

        VLevelAdapter adapter = new VLevelAdapter(getActivity(), bean.getList());
        common_recycle.setAdapter(adapter);
        adapter.setOnItemClickLitener(new VLevelAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ARouter.getInstance()
                        .build("/user/DetailsActivity")
                        .withInt("tag",4)
                        .withString("message", bean.getList().get(position).getName())
                        .withString("titleinfo","V级")
                        .navigation();
            }
        });
    }

    @Override
    public void getVmoneyData(VMoneyBean bean) {
        /**
         * V币
         * */
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.user_recycle_common_layout, null);
        TextView key = view.findViewById(R.id.top_key);
        TextView value = view.findViewById(R.id.top_value);
        LinearLayout ll_common=view.findViewById(R.id.ll_common);
        ll_common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance()
                        .build("/user/DetailsActivity")
                        .withInt("tag",1)
                        .withString("message", "V币是《小树林》APP为用户提供的三方币购买服务，V币在我们平台直接可以购买服务，V币得到的方式有很多，比如优惠充充充、签到、提的宝贵建议得到采纳、或我们的个个用户体验师群，以后还会陆续推出各种获得V币的形式哦！最新获取V币的形式请进入APP首页每日福利中查看！")
                        .navigation();
            }
        });


        key.setText("总V币");
        value.setText(bean.getVmoneycount() + "V币");
        common_recycle.addHeaderView(view);

        VmoneyAdapter adapter = new VmoneyAdapter(getActivity(), bean.getList());
        common_recycle.setAdapter(adapter);
        adapter.setOnItemClickLitener(new VmoneyAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ARouter.getInstance()
                        .build("/user/DetailsActivity")
                        .withInt("tag",4)
                        .withString("message", bean.getList().get(position).getName())
                        .withString("titleinfo","V币")
                        .navigation();
            }
        });
    }

    @Override
    public void getTryOutData(TryOutBean bean) {
        /**
         * 试用账号管理
         * */
        TryOutAdapter adapter = new TryOutAdapter(getActivity(), bean.getList());
        common_recycle.setAdapter(adapter);

        adapter.setOnItemClickLitener(new TryOutAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ARouter.getInstance()
                        .build("/user/UserAccountListActivity")
                        .withString("title", bean.getList().get(position).getName())
                        .withString("pid", bean.getList().get(position).getPid())
                        .navigation();
            }
        });
    }

    @Override
    public void getInformation(InformationBean bean) {
        /**消息列表*/
        InformationAdapter adapter=new InformationAdapter(getActivity(),bean.getList());
        common_recycle.setAdapter(adapter);
    }

    @Override
    public void getSupplyAccountlist(SupplyAccountBean bean) {
        /**
         * 提供账号的列表
         * */
        SupplyAccountAdapter adapter=new SupplyAccountAdapter(getActivity(),bean.getList());
        common_recycle.setAdapter(adapter);
        adapter.setOnItemClickLitener(new SupplyAccountAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                ARouter.getInstance()
                        .build("/activity/SupplyAccountActivity")
                        .withString("pid", bean.getList().get(position).getPid())
                        .withString("title",bean.getList().get(position).getName())
                        .withString("tag","0")
                        .navigation();
            }
        });

    }
}
