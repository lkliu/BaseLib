package com.liko.base.mvp.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Liko
 * @Date 17/10/28 下午5:05
 * @Description
 */

public abstract class BasePresenterImpl<V extends IBaseView> implements BasePresenter {
    public BasePresenterImpl(V view) {
        this.mRootview = view;
        start();
    }

    protected V mRootview;//给子类使用view


    @Override
    public void detach() {
        this.mRootview = null;
        unDisposable();
    }

    @Override
    public void start() {

    }

    /**
     * 检查是否销毁
     */
    public boolean checkNull() {
        if (mRootview == null) {
            return true;
        }
        return false;
    }

    //-----------------------我是分割线--------------------------------

    //以下下为配合RxJava2+retrofit2使用的

    /**
     * 将所有正在处理的Subscription都添加到CompositeSubscription中。统一退出的时候注销观察
     */
    private CompositeDisposable mCompositeDisposable;

    /**
     * 将Disposable添加
     *
     * @param subscription
     */
    @Override
    public void addDisposable(Disposable subscription) {
        //csb 如果解绑了的话添加 sb 需要新的实例否则绑定时无效的
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * 在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
     */
    @Override
    public void unDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
