package com.chaozhuo.familybrain;

import android.app.Application;

import com.chaozhuo.familybrain.net.CZSDKConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 18-8-10.
 */

public class App extends Application {
    public static Application app;
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        CZSDKConfig.initConfig(this);
        Logger.init("gamehelper").setLogLevel(LogLevel.FULL);
    }
}
