package com.liko.base.http;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * @author Liko
 * @Date 17/10/28 上午10:06
 * @Description 回调基类
 */

public abstract class ApiSubscriberCallBack<T> implements Subscriber<T> {
    @Override
    public void onSubscribe(Subscription s) {
        //不能遗漏
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * 请求完成
     */
    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable t) {
        onFailure(t);
    }

    /**
     * 加载成功
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * 加载出错
     *
     * @param t
     */
    public abstract void onFailure(Throwable t);
}
