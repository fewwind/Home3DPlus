package com.chaozhuo.threed.view.dialog;

import android.app.FragmentManager;
import android.os.Bundle;


/**
 * Created by fewwind on 18-5-24.
 */

public class DialogUtil {

    private static class SingleTon {
        public static DialogUtil util = new DialogUtil();
    }

    public static DialogUtil get() {
        return SingleTon.util;
    }

    public <T extends BaseDialog> T show(Class<T> cls, FragmentManager fragmentManager, DialogBean bean, IDialogClick click) {

        T t = null;
        try {
            t = cls.newInstance();
            Bundle bundle = new Bundle();
            bundle.putSerializable(BaseDialog.EXTRA_INFO, bean);
            t.setArguments(bundle);
            String tag = cls.getSimpleName();
            t.show(fragmentManager.beginTransaction(), tag);
            t.setListener(click);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
}
