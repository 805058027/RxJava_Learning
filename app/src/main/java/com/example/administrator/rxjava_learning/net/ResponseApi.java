package com.example.administrator.rxjava_learning.net;

import com.example.administrator.rxjava_learning.model.hotkey;
import com.example.administrator.rxjava_learning.model.login;
import com.example.administrator.rxjava_learning.model.register;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 接口
 */

public interface ResponseApi {

    /**
     * 玩Android注册接口
     *
     * @param username   用户名
     * @param password   密码
     * @param repassword 确认密码
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<register> getRegister(@Field("username") String username,
                                     @Field("password") String password,
                                     @Field("repassword") String repassword);

    /**
     * 玩Android登录接口
     *
     * @param username 用户名
     * @param password 密码
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<login> getLogin(@Field("username") String username,
                               @Field("password") String password);

    /**
     * 玩Android热搜关键字接口
     */
    @GET("hotkey/json")
    Observable<hotkey> getCall();
}
