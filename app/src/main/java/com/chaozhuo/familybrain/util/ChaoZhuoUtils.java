package com.chaozhuo.familybrain.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.chaozhuo.familybrain.App;

/**
 * Created by fewwind on 18-3-15.
 */

public class ChaoZhuoUtils {
    public static final String FROM_PHOENIX_ONE_LAUNCHER_FLAG = "from_phoenix_one_launcher_flag";
    public static final String FLAG_LAUNCH_FILEMANAGER_HOME = "flag_filemanager_home";
    public static final String FLAG_LAUNCH_FILEMANAGER_SHARE = "flag_filemanager_share";

    public static String[] PLATFORM = {"one", "arm", "x86"};
    private static final String META_DATA_CHANNEL = "UMENG_CHANNEL";

    public static void handleCaughtException(Throwable e) {
        LogUtils.e(e.toString());
    }

    public static String getPlantform() {
        if (isOneTv()) {
            return PLATFORM[0];
        } else {
            return PLATFORM[2];
        }
    }

    public static boolean isOneTv() {
        return !App.app.getPackageName().endsWith("x86");
    }

    public static final String FILE_MANAGER_PKG_NAME = "com.chaozhuo.filemanager";
    //    private static final String FILE_MANAGER_PKG_NAME = "com.ryan.filemanager";
    public static final String HTTP_TRANSFER_SERVICE_NAME = "com.chaozhuo.httptransfer.WebService";

    public static void openFileManager(Context ctx, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage(FILE_MANAGER_PKG_NAME);
        intent.putExtra(FROM_PHOENIX_ONE_LAUNCHER_FLAG, FLAG_LAUNCH_FILEMANAGER_SHARE);
        intent.setDataAndType(uri, "resource/folder");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    public static String getChannel(Context ctx) {
        return getMetadata(ctx, META_DATA_CHANNEL);
    }

    private static String getMetadata(Context context, String name) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                return appInfo.metaData.getString(name);
            }
        } catch (PackageManager.NameNotFoundException e) {
            ChaoZhuoUtils.handleCaughtException(e);
        }
        return null;
    }

    public static void startHttpTransferService(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(FILE_MANAGER_PKG_NAME, HTTP_TRANSFER_SERVICE_NAME));
        intent.setAction("com.chaozhuo.wifitransfer.action.INIT_WEB_SERVICE");
        context.startService(intent);
    }
}
