package com.xiaoshulin.vipbanlv.circleFrags;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiaoshulin.vipbanlv.circleFrags.fragment.CircleFragment1;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.CircleFragment2;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.CircleFragment3;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.CircleFragment4;
import com.xiaoshulin.vipbanlv.circleFrags.fragment.CircleFragment5;

import java.util.HashMap;

/**
 * Created by jipeng on 2018/12/19.
 */
public class MyCircleAdapter extends FragmentPagerAdapter {

    private HashMap<Integer, Fragment> mFragmentHashMap = new HashMap<>();
    private int num;

    public MyCircleAdapter(FragmentManager fm,int num) {
        super(fm);
        this.num=num;
    }

    @Override
    public Fragment getItem(int position) {
        return createFragment(position);
    }

    @Override
    public int getCount() {
        return num;
    }

    private Fragment createFragment(int pos) {
        Fragment fragment = mFragmentHashMap.get(pos);

        if (fragment == null) {
            switch (pos) {
                case 0:
                    fragment = CircleFragment1.newInstance();

                    break;
                case 1:
                    fragment =  CircleFragment2.newInstance();

                    break;
                case 2:
                    fragment = CircleFragment3.newInstance();

                    break;
                case 3:
                    fragment =  CircleFragment4.newInstance();

                    break;
                case 4:
                    fragment =  CircleFragment5.newInstance();

                    break;
            }
            mFragmentHashMap.put(pos, fragment);
        }
        return fragment;
    }

}
