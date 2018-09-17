package com.chaozhuo.familybrain.view.view3d;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fewwind on 18-8-27.
 */

public class ShadowView extends View {
    Paint mPaint;
    Path mPath;
    float[] mPathPos = new float[6];

    public ShadowView(Context context) {
        this(context, null);
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#E7232728"));
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float w = getMeasuredWidth();
        float h = getMeasuredHeight();
        mPath.moveTo(w * mPathPos[0], h * mPathPos[1]);
        mPath.lineTo(w * mPathPos[2], h * mPathPos[3]);
        mPath.lineTo(w * mPathPos[4], h * mPathPos[5]);
        mPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (mPath != null && !mPath.isEmpty())
//            canvas.drawPath(mPath, mPaint);
    }

    public void setPathPos(float[] args) {
        this.mPathPos = args;
    }
}
