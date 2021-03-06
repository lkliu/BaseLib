package com.liko.base.mvp.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.liko.base.R;
import com.liko.base.mvp.base.BaseApplication;
import com.liko.base.mvp.base.BasePresenter;
import com.liko.base.mvp.base.IBaseView;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Liko
 * @Date 17/10/28 下午4:09
 * @Description activity基类
 */

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements IBaseView {
    protected final String TAG = this.getClass().getSimpleName();
    protected BaseApplication mApplication;
    protected P mPresenter;
    public Context context;
    private Unbinder mUnbinder;
    protected Bundle bundle;
    protected Activity mActivity;
    /**
     * 是否加入到activity的list，管理
     */
    public static final String IS_NOT_ADD_ACTIVITY_LIST = "is_add_activity_list";
    protected int mColorId = R.color.bg_title;//状态栏的默认背景色
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        mApplication = (BaseApplication) getApplication();
        mPresenter = initPresenter();
        //如果intent包含了此字段,并且为true说明不加入到list
        // 默认为false,如果不需要管理(比如不需要在退出所有activity(killAll)时，退出此activity就在intent加此字段为true)
        boolean isNotAdd = false;
        if (getIntent() != null) {
            isNotAdd = getIntent().getBooleanExtra(IS_NOT_ADD_ACTIVITY_LIST, false);
        }
        if (!isNotAdd) {
            mApplication.getAppManager().addActivity(this);
        }
        setContentView(initView());
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        mActivity = this;
        initData();
        initStateBar();
    }

    private void initStateBar() {
        setmColorId(mColorId);
        if (isNeedLoadStatusBar()) {
            loadStateBar();
        }
    }

    private void loadStateBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            tintManager.setNavigationBarTintEnabled(true);
            //设置系统栏设置颜色
            //tintManager.setTintColor(R.color.red);
            //给状态栏设置颜色
            tintManager.setStatusBarTintResource(getmColorId());
            //Apply the specified drawable or color resource to the system navigation bar.
            //给导航栏设置资源
            tintManager.setNavigationBarTintResource(getmColorId());
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
//        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
//        win.setPadding(0, config.getPixelInsetTop(true), config.getPixelInsetRight(), config.getPixelInsetBottom());
    }

    public int getmColorId() {
        return mColorId;
    }

    public void setmColorId(int mColorId) {
        this.mColorId = mColorId;
    }


    /**
     * 子类是否需要实现沉浸式,默认需要
     *
     * @return
     */
    protected boolean isNeedLoadStatusBar() {
        return true;
    }


    protected abstract void initData();

    protected abstract int initView();

    @Override
    protected void onResume() {
        super.onResume();
        mApplication.getAppManager().setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mApplication.getAppManager().getCurrentActivity() == this) {
            mApplication.getAppManager().setCurrentActivity(null);
        }
    }

    @Override
    protected void onDestroy() {
        mApplication.getAppManager().removeActivity(this);
        if (mPresenter != null) {
            mPresenter.detach();//在presenter中解绑释放view
            mPresenter = null;
        }
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        this.mApplication = null;
        super.onDestroy();
    }

    /**
     * 在子类中初始化对应的presenter
     *
     * @return 相应的presenter
     */
    public abstract P initPresenter();


    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading(String msg) {

    }

    private long lastClickTime;

    public boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public boolean isFastClick(long time1) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < time1) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
