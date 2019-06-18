package com.xiaoshulin.vipbanlv.base;




public abstract class BaseMVPFragment<V, P extends BasePresenter<V>> extends BaseFragment{
    protected P presenter;

    @Override
    protected void initMvpPresenter() {
        super.initMvpPresenter();
        presenter = initPresenter();
        presenter.attach((V) this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        presenter.detach();
        super.onDetach();
    }


    public abstract P initPresenter();
}
