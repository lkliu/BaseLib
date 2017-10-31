package com.liko.guidelibrary;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.liko.base.mvp.ui.BaseActivity;
import com.liko.base.utils.PermissionsUtil;
import com.liko.guidelibrary.contract.LaunchContract;
import com.liko.guidelibrary.presenter.LaunchPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Liko
 * @Date 17/10/30 下午4:19
 * @Description 启动页
 */
@Route(path = "/launch/test")
public class LaunchActivity extends BaseActivity<LaunchContract.presenter> implements LaunchContract.View {
    private MyHandler handler;
    private RxPermissions permissions;

    @BindView(R2.id.tv_content)
    TextView tvContent;

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
    protected void initData() {
        String str = getIntent().getStringExtra("key");
        if (!TextUtils.isEmpty(str)) {
            ((TextView) findViewById(R.id.tv_content)).setText(str);
        }
        handler = new MyHandler(this);
        mPresenter.checkVersion();
        permissions = new RxPermissions(this);
        PermissionsUtil.callPhone(new PermissionsUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess(boolean flag) {
                handler.sendEmptyMessageDelayed(0, 2000);
                if (flag) {

                }
            }
        }, permissions, this);
    }

    @Override
    protected int initView() {
        return R.layout.activity_launch;
    }

    @Override
    public LaunchPresenter initPresenter() {
        return new LaunchPresenter(this);
    }

    @Override
    public void checkVersion() {

    }

    public static class MyHandler extends Handler {
        private final WeakReference<LaunchActivity> mhandleActivity;

        public MyHandler(LaunchActivity activity) {
            mhandleActivity = new WeakReference<LaunchActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mhandleActivity.get() == null) {
                return;
            }
            mhandleActivity.get().launchHome();
        }
    }

    /**
     * 启动首页
     */
    private void launchHome() {
        ARouter.getInstance().build("/home/main").navigation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
