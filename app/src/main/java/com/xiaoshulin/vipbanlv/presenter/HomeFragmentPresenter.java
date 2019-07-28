package com.xiaoshulin.vipbanlv.presenter;

import com.xiaoshulin.vipbanlv.base.BasePresenter;
import com.xiaoshulin.vipbanlv.bean.MainDataBean;
import com.xiaoshulin.vipbanlv.view.HomeView;

import java.util.ArrayList;

/**
 * Created by jipeng on 2018/9/23.
 */

public class HomeFragmentPresenter extends BasePresenter {
    HomeView homeView;

    public HomeFragmentPresenter(HomeView homeView) {
        this.homeView = homeView;
    }

    public void getDataInformation() {

        ArrayList<MainDataBean> list = new ArrayList<>();

        MainDataBean dataBean4 = new MainDataBean("每日福利", "小树林app每日福利来袭","https://mp.weixin.qq.com/s/CNLWNj9WtZCanWcZq87CUg");
        list.add(dataBean4);
        MainDataBean dataBean5 = new MainDataBean("优惠VIP账号", "账号密码购买等着你","http://www.vipbanlv.com");
        list.add(dataBean5);

        MainDataBean dataBean = new MainDataBean("订单", "你的购买记录都在这里","http://m.banlvnihao.icoc.cc");
        list.add(dataBean);

        MainDataBean dataBean10 = new MainDataBean("出租会员账号赚钱了", "必须是对应平台的会员账号哦","提供账号");
        list.add(dataBean10);

        MainDataBean dataBean11=new MainDataBean("寻租、赚钱租购社区","可寻租可赚钱","https://mp.weixin.qq.com/s/pQpu5ysd99O7JTlT0BL1Vw");
        list.add(dataBean11);

        MainDataBean dataBean1 = new MainDataBean("小树林生活", "名站一网打尽","http://www.vipbanlv.com/v2_test/#!/life");//http://m.banlvnihao.icoc.cc
        list.add(dataBean1);

        MainDataBean dataBean6 = new MainDataBean("央视、卫视电视直播", "带你走进电视世界","http://wap.leshi123.com");
        list.add(dataBean6);
          String url9="http://www.shengui.tv/?from=menu";
//        String url9="https://m.baidu.com/sf?openapi=1&dspName=iphone&from_sf=1&pd=baidulive_sf_page&top=%7B%22sfhs%22%3A13%7D&resource_id=4520&word=%E7%99%BE%E5%BA%A6%E7%9B%B4%E6%92%AD&title=%E6%90%9C%E7%B4%A2%E7%9B%B4%E6%92%AD%E8%81%9A%E5%90%88&ext=%7B%22sf_tab_name%22%3A%22";
        MainDataBean dataBean9 = new MainDataBean("直播大聚合", "女神、男神、游戏样样俱全",url9);  //http://dwz.cn/EuUvhs5K      http://www.shengui.tv/?from=menu
        list.add(dataBean9);

        MainDataBean dataBean2 = new MainDataBean("小树林客服", "账号问题，获取验证码","http://www.vipbanlv.com");
        list.add(dataBean2);
        MainDataBean dataBean7 = new MainDataBean("小树林V币充值", "V币实惠充值折上折","http://www.vipbanlv.com/v2_test/#!/recharge");
        list.add(dataBean7);
        MainDataBean dataBean3 = new MainDataBean("签到", "签到获得V币哦","http://www.vipbanlv.com/v2_test/#!/check-in");
        list.add(dataBean3);

        MainDataBean dataBean12 = new MainDataBean("进十轮现金红包群", "一号红包日等你来抢钱","https://mp.weixin.qq.com/s/Pd02bAWzAVDNih7IyKlU6A");
        list.add(dataBean12);

        MainDataBean dataBean8 = new MainDataBean("小树林分享", "缘分传递,不是每个朋友都值得拥有!","分享");//分享
        list.add(dataBean8);

        homeView.getListData(list);

    }
}
