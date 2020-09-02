package com.rapid.gyl.app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

/**
 * Created by gyl on 2020/9/2.
 */
public class App extends Application {

    public static Application mApp;

    public static Application getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        basicInit(this);
    }

    private void basicInit(Application application) {
        mApp = application;

        //sdk init
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
