package com.liko.guidelibrary.contract;

import com.liko.base.mvp.base.BasePresenter;
import com.liko.base.mvp.base.IBaseView;

/**
 * @author Liko
 * @Date 17/10/30 下午4:21
 * @Description
 */

public interface LaunchContract {
    interface View extends IBaseView {
        void checkVersion();
    }


    interface presenter extends BasePresenter {
        /**
         * 获取数据 检查版本更新
         */
        void checkVersion();
    }
}
