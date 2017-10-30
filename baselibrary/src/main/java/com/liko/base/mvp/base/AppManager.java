package com.liko.base.mvp.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Message;

import com.liko.base.mvp.ui.BaseActivity;
import com.liko.base.utils.LogUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Liko
 * @Date 17/10/28 下午4:55
 * @Description 用于管理所有activity, 和在前台的 activity
 * 可以通过直接持有AppManager对象执行对应方法
 * 也可以通过eventbus post事件,远程遥控执行对应方法
 */
public class AppManager {
    protected final String TAG = this.getClass().getSimpleName();
    public static final String APPMANAGER_MESSAGE = "appmanager_message";
    public static final int START_ACTIVITY = 0;
    public static final int SHOW_SNACKBAR = 1;
    public static final int KILL_ALL = 2;
    public static final int APP_EXIT = 3;
    public static final int KILL = 4;
    private Application mApplication;

    /**
     * 管理所有activity
     */
    public List<BaseActivity> mActivityList;
    /**
     * 当前在前台的activity
     */
    private BaseActivity mCurrentActivity;

    public AppManager(Application application) {
        this.mApplication = application;
    }

    private void dispatchStart(Message message) {
        if (message.obj instanceof Intent) {
            startActivity((Intent) message.obj);
        } else if (message.obj instanceof Class) {
            startActivity((Class) message.obj);
        }
        return;
    }

    /**
     * 让在前台的activity,打开下一个activity
     *
     * @param intent
     */
    public void startActivity(Intent intent) {
        if (getCurrentActivity() == null) {
            LogUtils.debugInfo("mCurrentActivity == null when startActivity(Intent)");
            //如果没有前台的activity就使用new_task模式启动activity
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mApplication.startActivity(intent);
            return;
        }
        getCurrentActivity().startActivity(intent);
    }

    /**
     * 让在前台的activity,打开下一个activity
     *
     * @param activityClass
     */
    public void startActivity(Class activityClass) {
        startActivity(new Intent(mApplication, activityClass));
    }

    /**
     * 释放资源
     */
    public void release() {
        mActivityList.clear();
        mActivityList = null;
        mCurrentActivity = null;
        mApplication = null;
    }

    /**
     * 将在前台的activity保存
     *
     * @param currentActivity
     */
    public void setCurrentActivity(BaseActivity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    /**
     * 获得当前在前台的activity
     *
     * @return
     */
    public BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }

    /**
     * 返回一个存储所有未销毁的activity的集合
     *
     * @return
     */
    public List<BaseActivity> getActivityList() {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        return mActivityList;
    }


    /**
     * 添加Activity到集合
     */
    public void addActivity(BaseActivity activity) {
        if (mActivityList == null) {
            mActivityList = new LinkedList<>();
        }
        synchronized (AppManager.class) {
            if (!mActivityList.contains(activity)) {
                mActivityList.add(activity);
            }
        }
    }

    public void finishExceptActivity(String name) {
        if (mActivityList == null) {
            LogUtils.debugInfo("mActivityList == null when finishExceptActivity(String)");
            return;
        }
        synchronized (AppManager.class) {
            if (name != null && name.trim().length() != 0) {
                String className;
                for (int i = 0, size = mActivityList.size(); i < size; i++) {
                    if (null != mActivityList.get(i)) {
                        className = mActivityList.get(i).getComponentName().getClassName();
                        if (!className.equals(name)) {
                            mActivityList.get(i).finish();
                        }
                    }
                }
            }
        }
    }

    /**
     * 删除集合里的指定activity
     *
     * @param activity
     */
    public void removeActivity(BaseActivity activity) {
        if (mActivityList == null) {
            LogUtils.debugInfo("mActivityList == null when removeActivity(BaseActivity)");
            return;
        }
        synchronized (AppManager.class) {
            if (mActivityList.contains(activity)) {
                mActivityList.remove(activity);
            }
        }
    }

    /**
     * 删除集合里的指定位置的activity
     *
     * @param location
     */
    public BaseActivity removeActivity(int location) {
        if (mActivityList == null) {
            LogUtils.debugInfo("mActivityList == null when removeActivity(int)");
            return null;
        }
        synchronized (AppManager.class) {
            if (location > 0 && location < mActivityList.size()) {
                return mActivityList.remove(location);
            }
        }
        return null;
    }

    /**
     * 关闭指定activity
     *
     * @param activityClass
     */
    public void killActivity(Class<?> activityClass) {
        if (mActivityList == null) {
            LogUtils.debugInfo("mActivityList == null when killActivity");
            return;
        }
        for (BaseActivity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                activity.finish();
            }
        }
    }


    /**
     * 指定的activity实例是否存活
     *
     * @param activity
     * @return
     */
    public boolean activityInstanceIsLive(BaseActivity activity) {
        if (mActivityList == null) {
            LogUtils.debugInfo("mActivityList == null when activityInstanceIsLive");
            return false;
        }
        return mActivityList.contains(activity);
    }


    /**
     * 指定的activity class是否存活(一个activity可能有多个实例)
     *
     * @param activityClass
     * @return
     */
    public boolean activityClassIsLive(Class<?> activityClass) {
        if (mActivityList == null) {
            LogUtils.debugInfo("mActivityList == null when activityClassIsLive");
            return false;
        }
        for (BaseActivity activity : mActivityList) {
            if (activity.getClass().equals(activityClass)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 关闭所有activity
     */
    public void killAll() {
        Iterator<BaseActivity> iterator = getActivityList().iterator();
        while (iterator.hasNext()) {
            iterator.next().finish();
            iterator.remove();
        }

    }


    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {
            killAll();
            if (mActivityList != null) {
                mActivityList = null;
            }
            ActivityManager activityMgr =
                    (ActivityManager) mApplication.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(mApplication.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 校验某个服务是否还存在
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        // 校验服务是否还存在
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = am.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : services) {
            // 得到所有正在运行的服务的名称
            String name = info.service.getClassName();
            if (serviceName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
