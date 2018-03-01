package com.example.administrator.rxjava_learning.App;

import android.app.Application;

/**
 * Created by Administrator on 2018/3/1.
 */

public class mApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.init(this);
    }
}
