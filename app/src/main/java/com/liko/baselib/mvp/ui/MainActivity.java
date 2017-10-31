package com.liko.baselib.mvp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.liko.base.entity.FristBean;
import com.liko.base.mvp.ui.BaseActivity;
import com.liko.base.utils.PermissionsUtil;
import com.liko.baselib.R;
import com.liko.baselib.R2;
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
@Route(path = "/home/main")
public class MainActivity extends BaseActivity<MainContract.presenter> implements MainContract.View {
    @BindView(R2.id.tv_content)
    TextView tvContent;

    private RxPermissions permissions;

    @Override
    protected void initData() {
        permissions = new RxPermissions(this);
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ARouter.getInstance().build("/launch/test").navigation();
//            }
//        });
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

    @OnClick({R2.id.button, R2.id.button1, R2.id.button2})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i==R.id.button){
            ARouter.getInstance().build("/launch/test").navigation();
        }
//        switch (view.getId()) {
//            case R.id.button:
//
//                break;
//            case R.id.button1:
//                PermissionsUtil.callPhone(new PermissionsUtil.RequestPermission() {
//                    @Override
//                    public void onRequestPermissionSuccess(boolean flag) {
//                        if (flag) {
//                            Toast.makeText(context, "请求打电话权限成功", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "请求权限取消", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, permissions, this);
//                break;
//            case R.id.button2:
//                PermissionsUtil.launchCamera(new PermissionsUtil.RequestPermission() {
//                    @Override
//                    public void onRequestPermissionSuccess(boolean flag) {
//                        if (flag) {
//                            Toast.makeText(context, "请求权限成功", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "请求权限取消", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, permissions, this);
//                break;
//            default:
//                break;
//        }

    }

}
