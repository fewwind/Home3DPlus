package com.chaozhuo.threed.transformer;

import android.view.View;

import com.chaozhuo.threed.view.ViewPager;

/**
 * Created by zhy on 16/5/7.
 */
public class NonPageTransformer implements ViewPager.PageTransformer
{
    @Override
    public void transformPage(View page, float position)
    {
        page.setScaleX(0.999f);//hack
    }

    public static final ViewPager.PageTransformer INSTANCE = new NonPageTransformer();
}
