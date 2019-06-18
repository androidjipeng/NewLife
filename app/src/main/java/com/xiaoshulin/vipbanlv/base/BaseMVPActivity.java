package com.xiaoshulin.vipbanlv.base;

import android.os.Bundle;


/**
 * Created by jp on 2018/4/12.
 */
public abstract class BaseMVPActivity<V,P extends BasePresenter<V>> extends BaseActivity {
    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        presenter.attach((V) this);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.detach();

        super.onDestroy();
    }

    public abstract P initPresenter();
    protected abstract void initData();
}
