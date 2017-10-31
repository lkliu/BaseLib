package com.liko.guidelibrary.presenter;

import com.liko.base.mvp.base.BasePresenterImpl;
import com.liko.guidelibrary.contract.LaunchContract;

/**
 * @author Liko
 * @Date 17/10/30 下午4:24
 * @Description
 */

public class LaunchPresenter extends BasePresenterImpl<LaunchContract.View> implements LaunchContract.presenter {
    public LaunchPresenter(LaunchContract.View view) {
        super(view);
    }

    @Override
    public void checkVersion() {

    }
}
