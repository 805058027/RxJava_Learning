package com.example.administrator.rxjava_learning.App;

import android.content.Context;

public class AppContext {
    private static final Object lock = new Object();
    private static AppContext appInstance;
    private final Context mContext;

    public AppContext(Context context) {
        this.mContext = context;
    }

    public static void init(Context context) {
        if (appInstance == null) {
            synchronized (lock) {
                if (appInstance == null) {
                    appInstance = new AppContext(context);
                }
            }
        }
    }

    public Context getContext() {
        return mContext;
    }

    public static AppContext getInstance() {
        return appInstance;
    }
}
