package com.liko.base.utils;

import com.liko.base.mvp.base.IBaseView;
import com.liko.base.mvp.ui.BaseActivity;
import com.liko.base.mvp.ui.BaseFragment;
import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * @author Liko
 * @Date 17/10/30 上午10:05
 * @Description RXLifecycle
 */

public class RxHelper {
    public static <T> LifecycleTransformer<T> bindToLifecycle(IBaseView view) {
        if (view instanceof BaseActivity) {
            return ((BaseActivity) view).<T>bindToLifecycle();
        } else if (view instanceof BaseFragment) {
            return ((BaseFragment) view).<T>bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't activity or fragment");
        }
    }
}
