package com.liko.base.mvp.base;

import android.os.Bundle;

/**
 * @author Liko
 * @Date 17/10/28 下午2:34
 * @Description View基类
 */

public interface IBaseView {
    /**
     * 显示加载
     */
    void showLoading(String msg);

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示信息
     */
    void showMessage(String message);

    /**
     * 跳转activity
     *
     * @param activityClass 目标
     * @param bundle        参数
     * @param requestCode   约定的key
     */
    void launchActivity(Class activityClass, Bundle bundle, int requestCode);

    /**
     * 杀死自己
     */
    void killMyself();

    void showLoadingLayout();//显示加载中

    void showEmptyLayout();//显示无数据

    void showErrorLayout();//显示错误

    void showNoNetworkLayout();//显示无网络

    void showSuccessLayout();//显示加载成功
}
