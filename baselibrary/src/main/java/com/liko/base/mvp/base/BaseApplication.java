package com.liko.base.mvp.base;

import android.app.Application;
import android.content.Context;

/**
 * @author Liko
 * @Date 17/10/28 下午4:30
 * @Description application基类
 */
public abstract class BaseApplication extends Application {
    static private BaseApplication mApplication;
    protected AppManager mAppManager;
    protected final String TAG = this.getClass().getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        if (mAppManager == null) {
            mAppManager = new AppManager(mApplication);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    /**
     * 程序终止的时候执行 释放资源
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppManager != null) {
            this.mAppManager.release();
            this.mAppManager = null;
        }
        if (mApplication != null) {
            mApplication = null;
        }
    }

    public AppManager getAppManager() {
        return mAppManager;
    }

    /**
     * 返回上下文
     *
     * @return
     */
    public static Context getContext() {
        return mApplication;
    }

}
