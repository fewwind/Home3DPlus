package com.chaozhuo.familybrain.net;

import android.app.Application;
import android.os.Environment;

import com.chaozhuo.familybrain.BuildConfig;
import com.chaozhuo.familybrain.util.ChaoZhuoUtils;
import com.chaozhuo.updateconfig.SDKConfig;

import java.io.File;

/**
 * Created by fewwind on 17-12-8.
 */

public class CZSDKConfig {

    public static void initConfig(Application app) {

        if (BuildConfig.DEBUG) {
//            setTestConfig(app);
            setRelaseConfig(app);
        } else {
            // 正式服务器
            File file = new File(Environment.getExternalStorageDirectory() + File.separator
                    + ".chaozhuousetestserver");
            if (ChaoZhuoUtils.getChannel(app).equals("private_beta")) {
                if (file.exists()) {
                    setTestConfig(app);
                } else {
                    setRelaseConfig(app);
                }
            } else {
                if (file.exists() && (System.currentTimeMillis() < 1525276800000l)) { // 五一之前用测试
                    setTestConfig(app);
                } else {
                    setRelaseConfig(app);
                }
            }
        }
    }

    private static void setRelaseConfig(Application app) {
        if (ChaoZhuoUtils.isOneTv()) {
            SDKConfig.init(app).setmHost("http://api.phenixos.com")
                    .setmApiKey("c1804240000001001")
                    .setmSecretKey("058819ad51154012a59df8f8d42c7a7f").build();
        } else {
            SDKConfig.init(app).setmHost("http://api.phenixos.com")
                    .setmApiKey("c1804240000001002")
                    .setmSecretKey("ecdf0be902e04af5ba2e4fbea1c036c4").build();
        }
    }

    private static void setTestConfig(Application app) {
        if (ChaoZhuoUtils.isOneTv()) {
            SDKConfig.init(app).setmHost("http://api.test.chaozhuo.org")
                    .setmApiKey("c1803010000000601")
                    .setmSecretKey("6fd572fb3336441b8ca8dd26d6077033").build();
        } else {
            SDKConfig.init(app).setmHost("http://api.test.chaozhuo.org")
                    .setmApiKey("c1804240000000701")
                    .setmSecretKey("0d2bd88bafa54c11b253e0448792d0be").build();
        }
    }
}
