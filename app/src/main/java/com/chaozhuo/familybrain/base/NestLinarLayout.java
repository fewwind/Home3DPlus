package com.chaozhuo.familybrain.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaozhuo.familybrain.R;
import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 18-8-10.
 */

public class NestLinarLayout extends LinearLayout implements NestedScrollingParent2 {

    TextView tv1;
    TextView tv2;
    int height = 0;

    public NestLinarLayout(Context context) {
        this(context, null);
    }

    public NestLinarLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestLinarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

    }

    int offsetY = 0;

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        Logger.v("dyConsumed = " + dyConsumed + " unConsume = " + dyUnconsumed + " getScrollY= " + getScrollY());
//        if (dyConsumed > 0 && getScrollY() < height) {
//            scrollBy(0, dyConsumed);
//        }
        if (dyUnconsumed < 0 && getScrollY() > 0) {
            scrollBy(0, dyUnconsumed);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
//            params.height = H + Math.max(getScrollY(), 0);
//            params.height = Math.min(1080, params.height);
//            setLayoutParams(params);
            Logger.w("dy = " + dyUnconsumed + " getScrollY = " + getScrollY() + " = " + params.height);
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

        if (dy > 0 && getScrollY() < height) {
            scrollBy(0, dy);
            consumed[1] = dy;
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
//            params.height = H + getScrollY();
//            params.height = Math.min(1280, params.height);
//            setLayoutParams(params);
            Logger.i("dy = " + dy + " consumed = " + consumed[1] + " = " + params.height);
        }
//        if (dy < 0 && getScrollY() > 0) {
//            consumed[1] = dy;
//            scrollBy(0, dy);
//        }
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) y = 0;
        if (y > height) y = height;
        super.scrollTo(x, y);
    }

    private void show() {

    }

    private void hide() {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
    }

    int H = 1080;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        height = tv1.getMeasuredHeight() * 2;
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        params.height = H + height;
        setLayoutParams(params);
    }
}
