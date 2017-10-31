package com.liko.baselib.mvp;

import com.alibaba.android.arouter.launcher.ARouter;
import com.liko.base.mvp.base.BaseApplication;
import com.liko.baselib.BuildConfig;

/**
 * @author Liko
 * @Date 17/10/30 下午2:33
 * @Description
 */

public class WeApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }
}
