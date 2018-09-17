package com.chaozhuo.familybrain.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.chaozhuo.familybrain.App;

/**
 * Created by ryanhuen on 18-3-20.
 */

public class ResourceUtils {
    public static int getDrawableIdByVar(String var) {
        try {
            final int id = App.app.getResources().getIdentifier(var, "drawable",
                    App.app.getPackageName());
            return id;
        } catch (final Exception e) {
        }
        return 0;
    }

    // 获取高清icon
    public synchronized static Drawable getIconFromPackageName(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            Context otherAppCtx = context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
            int displayMetrics[] = {
                    DisplayMetrics.DENSITY_XXXHIGH,
                    DisplayMetrics.DENSITY_XXHIGH,
                    DisplayMetrics.DENSITY_XHIGH,
                    DisplayMetrics.DENSITY_HIGH,
                    DisplayMetrics.DENSITY_TV};
            for (int displayMetric : displayMetrics) {
                try {
                    Drawable d = otherAppCtx.getResources().getDrawableForDensity(pi.applicationInfo.icon,
                            displayMetric);
                    if (d != null) {
                        return d;
                    }
                } catch (Resources.NotFoundException e) {
                    continue;
                }
            }
        } catch (Exception e) {
            // Handle Error here
        }
        ApplicationInfo appInfo = null;
        try {
            appInfo = pm.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        return appInfo.loadIcon(pm);
    }
}
