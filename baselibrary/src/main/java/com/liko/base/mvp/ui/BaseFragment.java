package com.liko.base.mvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liko.base.mvp.base.BasePresenter;
import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Liko
 * @Date 17/10/28 下午4:58
 * @Description Fragment基类
 */

public abstract class BaseFragment<P extends BasePresenter> extends RxFragment {
    protected BaseActivity mActivity;
    protected View mRootView;
    protected final String TAG = this.getClass().getSimpleName();
    protected P mPresenter;
    private Unbinder mUnbinder;
    protected Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView();
            //绑定到butterknife
            mUnbinder = ButterKnife.bind(this, mRootView);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        initData();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();//释放资源
        }
        this.mPresenter = null;
        this.mActivity = null;
        this.mRootView = null;
        this.mUnbinder = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return true;
    }

    protected abstract View initView();

    protected abstract void initData();

    public void setData(Object data) {

    }

    public void setData() {

    }

    long lastClickTime;

    protected boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
