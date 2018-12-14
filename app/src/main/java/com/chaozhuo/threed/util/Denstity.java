package com.chaozhuo.threed.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import com.orhanobut.logger.Logger;

import java.util.Arrays;

/**
 * Created by fewwind on 18-8-21.
 */

public class Denstity {
    private static float density = 0;
    private static float scale = 0;

    public static void setDensity(final Application app, Activity activity) {
        final DisplayMetrics metricsApp = app.getResources().getDisplayMetrics();
        Logger.v("fitstDsiplay = " + metricsApp.toString());
        if (density == 0) {
            density = metricsApp.density;
            scale = metricsApp.scaledDensity;
            app.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        Logger.e("New config = " + metricsApp.toString());
                        scale = app.getResources().getDisplayMetrics().scaledDensity;
                        Logger.e("New config = " + app.getResources().getDisplayMetrics().toString());
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        float targetDensity = metricsApp.widthPixels / 480;
        float targetScale = targetDensity * (metricsApp.scaledDensity / metricsApp.density);
        float targetDensityDpi = 160 * targetDensity;

        metricsApp.density = targetDensity;
        metricsApp.densityDpi = (int) targetDensityDpi;
        metricsApp.scaledDensity = targetScale;
        Logger.d("修改后 = " + metricsApp.toString());
        DisplayMetrics metricsActivity = activity.getResources().getDisplayMetrics();
        Logger.i("activityDsiplay = " + metricsApp.toString());
        metricsActivity.density = targetDensity;
        metricsActivity.densityDpi = (int) targetDensityDpi;
        metricsActivity.scaledDensity = targetScale;
        int[] ss = new int[2];
        ss[5] = 23;
        int[] newSs = new int[6];
        for (int i = 0; i < newSs.length; i++) {
            newSs[i] = ss[i];
        }
        Logger.w("是不是"+Arrays.toString(newSs));
    }
}
