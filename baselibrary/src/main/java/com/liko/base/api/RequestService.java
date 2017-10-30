package com.liko.base.api;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.liko.base.entity.FristBean;
import com.liko.base.http.HttpResponse;

/**
 * @author Liko
 * @Date 17/10/28 上午9:04
 * @Description retrofit 接口定义
 */

public interface RequestService {
    /**
     * 获取数据 https://api.douban.com/v2/book/search?q=github&tag=&start=0&count=1
     *
     * @return 数据
     */
    @GET("data/Android/10/1")
    Flowable<FristBean> getGithub();
}
