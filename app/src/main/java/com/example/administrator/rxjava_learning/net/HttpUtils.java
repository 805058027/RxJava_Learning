package com.example.administrator.rxjava_learning.net;

import com.example.administrator.rxjava_learning.net.client.MyOkHttpClient;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 请求地址的配置
 */

public class HttpUtils {
    private static ResponseApi api;

    public static ResponseApi getInstance() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.wanandroid.com/") // 设置 网络请求 Url
                    .client(MyOkHttpClient.getSaveHttpClient())
                    .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                    .build();
            api = retrofit.create(ResponseApi.class);
        }
        return api;
    }
}
