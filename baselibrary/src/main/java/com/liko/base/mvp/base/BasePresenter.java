package com.liko.base.mvp.base;

import io.reactivex.disposables.Disposable;

/**
 * @author Liko
 * @Date 17/10/28 下午3:14
 * @Description
 */
public interface BasePresenter {
    /**
     * 默认初始化
     */
    void start();

    /**
     * Activity关闭把view对象置为空
     */
    void detach();

    /**
     * 将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
     *
     * @param subscription
     */
    void addDisposable(Disposable subscription);

    /**
     * 注销所有请求
     */
    void unDisposable();

}
