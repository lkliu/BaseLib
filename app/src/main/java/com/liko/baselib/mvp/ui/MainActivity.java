package com.liko.baselib.mvp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liko.base.entity.FristBean;
import com.liko.base.mvp.ui.BaseActivity;
import com.liko.base.utils.PermissionsUtil;
import com.liko.baselib.R;
import com.liko.baselib.mvp.contract.MainContract;
import com.liko.baselib.mvp.presenter.MainPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

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

    private RxPermissions permissions;

    @Override
    protected void initData() {
        permissions = new RxPermissions(this);
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

    @OnClick({R.id.button, R.id.button1, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                mPresenter.getFristBean();
                break;
            case R.id.button1:
                PermissionsUtil.callPhone(new PermissionsUtil.RequestPermission() {
                    @Override
                    public void onRequestPermissionSuccess(boolean flag) {
                        if (flag) {
                            Toast.makeText(context, "请求打电话权限成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "请求权限取消", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, permissions, this);
                break;
            case R.id.button2:
                PermissionsUtil.launchCamera(new PermissionsUtil.RequestPermission() {
                    @Override
                    public void onRequestPermissionSuccess(boolean flag) {
                        if (flag) {
                            Toast.makeText(context, "请求权限成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "请求权限取消", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, permissions, this);
                break;
            default:
                break;
        }

    }
}
