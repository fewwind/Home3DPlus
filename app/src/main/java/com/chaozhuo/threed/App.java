package com.chaozhuo.threed;

import android.app.Application;

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
        Logger.init("gamehelper").setLogLevel(LogLevel.FULL);
    }
}
