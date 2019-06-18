package com.xiaoshulin.vipbanlv.base;

/**
 * Created by jp on 2018/4/12.
 */
public abstract class BasePresenter<T> {

    public T view;

    public void attach(T view){
        this.view = view;
    }

    public void detach(){
        this.view = null;
    }

}
