package com.chaozhuo.threed.util;

import android.app.Activity;
import android.hardware.input.InputManager;
import android.view.InputEvent;
import android.view.MotionEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InjectService {

    static final String TAG = "InjectService";

    private final static InjectService sInstance = new InjectService();

    public static InjectService get() {
        return sInstance;
    }


    public void invokeInjectInputEvent(final Activity activity, final MotionEvent event) {
//        if (true)return;
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {
//                        activity.dispatchTouchEvent(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            return;
        }
        Class cl = InputManager.class;
        try {
            Method method = cl.getMethod("getInstance");
            Object result = method.invoke(cl);
            InputManager im = (InputManager) result;
            method = cl.getMethod("injectInputEvent", InputEvent.class, int.class);
            method.invoke(im, event, 0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}