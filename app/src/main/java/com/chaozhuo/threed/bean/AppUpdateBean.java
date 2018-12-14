package com.chaozhuo.threed.bean;

import java.io.Serializable;

/**
 * Created by fewwind on 18-4-17.
 */

public class AppUpdateBean implements Serializable {
    private String app_id;
    private int version_code;
    private String download_url;
    private String icon_160;
    private String cover_vert;
    private String cover_hori;

    public AppUpdateBean(String app_id, int version_code, String download_url) {
        this.app_id = app_id;
        this.version_code = version_code;
        this.download_url = download_url;
    }
    public AppUpdateBean(String app_id, int version_code) {
        this.app_id = app_id;
        this.version_code = version_code;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getIcon_160() {
        return icon_160;
    }


    public String getCover_hori() {
        return cover_hori;
    }

    public void setCover_hori(String cover_hori) {
        this.cover_hori = cover_hori;
    }

    public String getCover_vert() {
        return cover_vert;
    }

    public void setCover_vert(String cover_vert) {
        this.cover_vert = cover_vert;
    }

    @Override
    public String toString() {
        return "AppUpdateBean{" +
                "app_id='" + app_id + '\'' +
                ", version_code=" + version_code +
                ", download_url='" + download_url + '\'' +
                '}';
    }
}
