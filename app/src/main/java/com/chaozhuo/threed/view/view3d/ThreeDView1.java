package com.chaozhuo.threed.view.view3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 18-8-27.
 */

public class ThreeDView1 extends ImageView {
    Paint mPaint;
    boolean isHasFocus;
    Path mPath;
    Canvas mCanvas;

    public ThreeDView1(Context context) {
        this(context, null);
    }

    public ThreeDView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeDView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mCanvas = new Canvas();
        setFocusable(true);
        mPath = new Path();
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isHasFocus = hasFocus;
                if (hasFocus) {
                } else {
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (mDst == null) return;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Matrix matrix = new Matrix();
        float w = getMeasuredWidth();
        float h = getMeasuredHeight();
        int bw = bitmap.getWidth();
        int bh = bitmap.getHeight();
//        matrix.postScale(w / bw, h / bh);
        Logger.v(w / bw + "canver" + w);
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, (int) w, (int) h, true);

        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG)); //设置图形、图片的抗锯齿。可用于线条等。按位或.

        float[] src = {0, 0, 0, h, w, h, w, 0};
        float[] dst = {mDst[0] - 0, mDst[1] - 0, mDst[2] - 0, h - mDst[3], w - mDst[4], h - mDst[5], w - mDst[6], mDst[7]};
        matrix.setPolyToPoly(src, 0, dst, 0, 4);
//        matrix.postRotate(20);
        canvas.drawBitmap(bitmap1, matrix, mPaint);
        mPaint.setColor(Color.parseColor("#57abb4"));
//        mPaint.setColor(Color.RED);
        int pW = 4;
        mPaint.setStrokeWidth(pW);
        mPath.reset();
        pW = pW / 4;
        mPath.moveTo(dst[0], dst[1] + pW);
        mPath.lineTo(dst[6], dst[7] + pW);
        mPath.moveTo(dst[2], dst[3] - pW);
        mPath.lineTo(dst[4], dst[5] - pW);
        canvas.drawPath(mPath, mPaint);
        if (isHasFocus) {
            int paintW = 4;
            mPaint.setStrokeWidth(paintW);
            mPaint.setColor(Color.parseColor("#007EFD"));
            paintW = paintW / 2;
            mPath.reset();
            mPath.moveTo(dst[0] + paintW, dst[1] + paintW);
            mPath.lineTo(dst[2] + paintW, dst[3] - paintW);
            mPath.lineTo(dst[4] - paintW, dst[5] - paintW);
            mPath.lineTo(dst[6] - paintW, dst[7] + paintW);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }
    }

    private float[] mDst;

    public void setDst(float[] dst) {
        this.mDst = dst;
    }

}
