package com.liko.baselib.mvp.contract;

import com.liko.base.entity.FristBean;
import com.liko.base.mvp.base.BasePresenter;
import com.liko.base.mvp.base.IBaseView;

/**
 * @author Liko
 * @Date 17/10/28 下午3:20
 * @Description
 */

public interface MainContract {

    interface View extends IBaseView {
        void setDate(FristBean fristBean);
    }


    interface presenter extends BasePresenter {
        /**
         * 获取数据
         */
        void getFristBean();
    }
}
