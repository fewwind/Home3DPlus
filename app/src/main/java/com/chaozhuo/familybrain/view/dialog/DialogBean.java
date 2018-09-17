package com.chaozhuo.familybrain.view.dialog;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by fewwind on 18-5-24.
 */

public class DialogBean implements Serializable {


    public String title;
    public String ok;
    public String cancle;

    public boolean isShowEdit;
    public int stateIcon;
    public int okColorId;
    public int cancelColorId;

    public static class Builder {
        public String title;
        public String ok;
        public String cancle;
        public boolean isShowEdit;
        public int stateIcon;
        public int okColorId;
        public int cancelColorId;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setOk(String ok) {
            this.ok = ok;
            return this;
        }

        public Builder setCancle(String cancle) {
            this.cancle = cancle;
            return this;
        }

        public Builder setTitle(Context context, int title) {
            this.title = context.getResources().getString(title);
            return this;
        }

        public Builder setOk(Context context, int ok) {
            this.ok = context.getResources().getString(ok);
            return this;
        }

        public Builder setCancle(Context context, int cancle) {
            this.cancle = context.getResources().getString(cancle);
            return this;
        }


        public Builder setIsShowEdit(boolean flag) {
            this.isShowEdit = flag;
            return this;
        }

        public Builder setStateIcon(int icon) {
            this.stateIcon = icon;
            return this;
        }

        public Builder setOkColor(int colorId) {
            this.okColorId = colorId;
            return this;
        }

        public Builder setCancelColor(int colorId) {
            this.cancelColorId = colorId;
            return this;
        }

        public DialogBean build() {
            DialogBean bean = new DialogBean();
            bean.title = title;
            bean.ok = ok;
            bean.cancle = cancle;
            bean.isShowEdit = isShowEdit;
            bean.stateIcon = stateIcon;
            bean.okColorId = okColorId;
            bean.cancelColorId = cancelColorId;
            return bean;
        }
    }
}
