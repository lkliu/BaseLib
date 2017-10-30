package com.liko.baselib.mvp.presenter;


import com.liko.base.api.ApiManager;
import com.liko.base.entity.FristBean;
import com.liko.base.http.ApiSubscriberCallBack;
import com.liko.base.mvp.base.BasePresenterImpl;
import com.liko.base.utils.RxHelper;
import com.liko.baselib.mvp.contract.MainContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Liko
 * @Date 17/10/28 下午4:19
 * @Description
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.presenter {

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void getFristBean() {
        ApiManager
                .getInstance()
                .getRequestService()
                .getGithub()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxHelper.<FristBean>bindToLifecycle(mRootview))
                .subscribe(new ApiSubscriberCallBack<FristBean>() {
                    @Override
                    public void onSuccess(FristBean fristBean) {
                        if (checkNull()) {
                            return;
                        }
                        mRootview.setDate(fristBean);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }
}
