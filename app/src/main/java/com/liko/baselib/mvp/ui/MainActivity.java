package com.liko.baselib.mvp.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.liko.base.entity.FristBean;
import com.liko.base.mvp.ui.BaseActivity;
import com.liko.baselib.R;
import com.liko.baselib.mvp.contract.MainContract;
import com.liko.baselib.mvp.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Liko
 * @Date 17/10/27 下午6:28
 * @Description
 */
public class MainActivity extends BaseActivity<MainContract.presenter> implements MainContract.View {
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void initData() {

    }

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    public MainContract.presenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void launchActivity(Class activityClass, Bundle bundle, int requestCode) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void showLoadingLayout() {

    }

    @Override
    public void showEmptyLayout() {

    }

    @Override
    public void showErrorLayout() {

    }

    @Override
    public void showNoNetworkLayout() {

    }

    @Override
    public void showSuccessLayout() {
    }

    @Override
    public void setDate(FristBean fristBean) {
        tvContent.setText(fristBean.getResults().size() + "--");
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        mPresenter.getFristBean();
    }
}
