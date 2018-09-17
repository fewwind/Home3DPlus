package com.chaozhuo.familybrain.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by fewwind on 18-8-28.
 */

public class UltraDepthScaleTransformer2 implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.5f;
    private static final float MAX_ROTATION = 30;

    @Override
    public void transformPage(View view, float position) {
        final float scale = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
        final float rotation = MAX_ROTATION * Math.abs(position);

        if (position <= 0f) {
            view.setTranslationX(view.getWidth() * -position * 0.3f);
            view.setPivotY(0.5f * view.getHeight());
            view.setPivotX(0.5f * view.getWidth());
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setRotationY(rotation);
        } else if (position <= 1f) {
            view.setTranslationX(view.getWidth() * -position * 0.3f);
            view.setPivotY(0.5f * view.getHeight());
            view.setPivotX(0.5f * view.getWidth());
            view.setScaleX(scale);
            view.setScaleY(scale);
            view.setRotationY(-rotation);
        }
    }
}