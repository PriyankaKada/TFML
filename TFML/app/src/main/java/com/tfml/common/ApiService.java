package com.tfml.common;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.tfml.auth.Constant;
import com.tfml.auth.TmflApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by webwerks on 29/7/16.
 */

public class ApiService {
    TmflApi services;
    static ApiService apiService;
    private static Retrofit retrofit;


    public static ApiService getInstance() {
        if (apiService == null) {
            synchronized (ApiService.class) {

                apiService = new ApiService();


                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.networkInterceptors().add(new StethoInterceptor());
                retrofit = new Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return apiService;
    }


    public TmflApi call() {
        services = retrofit.create(TmflApi.class);
        return services;
    }


}
