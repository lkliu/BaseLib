package com.liko.base.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.liko.base.http.MyInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Liko
 * @Date 17/10/28 上午9:38
 * @Description 接口管理类
 */
public class ApiManager {
    public RequestService apiService;
    private static ApiManager apiManager;

    public synchronized static ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public RequestService getRequestService() {
        return getRequestService(null);
    }

    public RequestService getRequestService(String baseUrl) {
        if (apiService == null) {
            OkHttpClient okClient = new OkHttpClient.Builder().addInterceptor(new MyInterceptor()).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl == null ? ApiConfig.BASEURL_TWO : baseUrl)
                    .client(okClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(RequestService.class);
        }
        return apiService;
    }
}
