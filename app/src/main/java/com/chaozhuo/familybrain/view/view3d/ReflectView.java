package com.chaozhuo.familybrain.view.view3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 18-8-27.
 */

public class ReflectView extends ImageView {
    Paint mPaint;
    boolean isHasFocus;
    Path mPath;
    Canvas mCanvas;

    public ReflectView(Context context) {
        this(context, null);
    }

    public ReflectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReflectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mCanvas = new Canvas();
        setFocusable(false);
        mPath = new Path();
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
        matrix.preScale(1, -1);
        Logger.v(w + " 倒影 " + h);
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, (int) w, (int) h * 2, false);

        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG)); //设置图形、图片的抗锯齿。可用于线条等。按位或.
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap1, 0, (int) h, (int) w, (int) h, matrix, false);
        Bitmap bitmap3 = Bitmap.createBitmap((int)w,(int)h,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bitmap3);
        mCanvas.drawBitmap(bitmap2,0,0,mPaint);
        float[] src = {0, 0, 0, h, w, h, w, 0};
        float[] dst = {mDst[0] - 0, mDst[1] - 0, mDst[2] - 0, h - mDst[3], w - mDst[4], h - mDst[5], w - mDst[6], mDst[7]};
        matrix.reset();
        matrix.setPolyToPoly(src, 0, dst, 0, 4);
//        mCanvas.drawBitmap(bitmap2, 0, 0, mPaint);
        LinearGradient lg = new LinearGradient(0, 0, 0, h, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.MIRROR);
//        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(lg);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mCanvas.drawRect(0, 0, w, h, mPaint);
        mPaint.setXfermode(null);

        canvas.drawBitmap(bitmap3, 0, 0, null);
//        mPaint.setColor(Color.parseColor("#57abb4"));
//        int pW = 4;
//        mPaint.setStrokeWidth(pW);
//        mPath.reset();
//        pW = pW / 4;
//        mPath.moveTo(dst[0], dst[1] + pW);
//        mPath.lineTo(dst[6], dst[7] + pW);
//        mPath.moveTo(dst[2], dst[3] - pW);
//        mPath.lineTo(dst[4], dst[5] - pW);
//        canvas.drawPath(mPath, mPaint);
    }

    private float[] mDst  = {0, 0, 0, 0, 0, 0, 0, 0};

    public void setDst(float[] dst) {
        this.mDst = dst;
    }

}
