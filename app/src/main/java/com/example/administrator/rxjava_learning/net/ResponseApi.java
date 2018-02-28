package com.example.administrator.rxjava_learning.net;

import com.example.administrator.rxjava_learning.model.hotkey;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/2/28.
 */

public interface ResponseApi {

    /**
     * 玩Android注册接口
     */

    /**
     *  玩Android登录接口
     */

    /**
     * 玩Android热搜关键字接口
     */
    @GET("hotkey/json")
    Observable<hotkey> getCall();
}
