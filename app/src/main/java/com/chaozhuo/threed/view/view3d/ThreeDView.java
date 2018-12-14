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
import android.support.v4.graphics.ColorUtils;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;

import com.chaozhuo.threed.util.DensityUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by fewwind on 18-8-27.
 */

public class ThreeDView extends android.support.v7.widget.AppCompatImageView {
    Paint mPaint;
    boolean isHasFocus;
    Path mPath;
    Canvas mCanvas;
    int boundColor = 0;
    int bottomTxtHeight = DensityUtil.dip2px(20);
    float w, h;
    Line mLine = Line.No;
    int mBoundW = DensityUtil.dip2px(2);// focus 和透明边框的高度

    public ThreeDView(Context context) {
        this(context, null);
    }

    public ThreeDView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeDView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
                invalidate();
            }
        });
//        Logger.i("ThreeDView = 构造方法");
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        Logger.v("ThreeDView = onAttachedToWindow");
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
        if (mDst == null || bitmapDrawable == null) return;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Matrix matrix = new Matrix();
        w = getMeasuredWidth();
        h = getMeasuredHeight();
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, (int) w, (int) h - mBoundW, true);// 图片两边各留画笔宽度
        handBound(bitmap1);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG)); //设置图形、图片的抗锯齿。可用于线条等。按位或.

        float[] src = {0, 0, 0, h, w, h, w, 0};
        float[] dst = {mDst[0] - 0, mDst[1] - 0, mDst[2] - 0, h - mDst[3], w - mDst[4], h - mDst[5], w - mDst[6], mDst[7]};
        matrix.setPolyToPoly(src, 0, dst, 0, 4);
//        matrix.postRotate(20);
        Bitmap bitmapEmpty = Bitmap.createBitmap((int) w, (int) h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bitmapEmpty);
        mCanvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG)); //设置图形、图片的抗锯齿。可用于线条等。按位或.
        mPaint.setStrokeWidth(mBoundW * 2);// 画笔绘制的时候以中心点算起
        mPaint.setColor(Color.RED);// 每次绘制之前最好重设颜色，避免别的颜色干扰
        mCanvas.drawBitmap(bitmap1, 0, mBoundW / 2, mPaint);
        mPaint.setColor(Color.TRANSPARENT);
        mCanvas.drawRect(0, 0, w, h, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawBitmap(bitmapEmpty, matrix, mPaint);
        drawBottomTextBg(canvas);
        drawTopTextBg(canvas);
        drawSingleBound();
        drawFocus(canvas);
    }

    private float[] mDst;

    public ThreeDView setDst(float[] dst) {
        this.mDst = dst;
        setLine();
        return this;
    }

    private void drawBottomTextBg(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mBoundW);
        int paintW = mBoundW / 2;
        mPaint.setColor(Color.parseColor("#80000000"));
        mPath.reset();
        mPath.moveTo(0, h - bottomTxtHeight - mDst[3]);
        mPath.lineTo(0, h - mDst[3] - paintW);
        mPath.lineTo(w, h - mDst[5] - paintW);
        mPath.lineTo(w, h - bottomTxtHeight - mDst[5]);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawSingleBound() {
        if (mLine != Line.No && boundColor != 0) {
            mPaint.setColor(boundColor);
            if (mLine == Line.Left) {
                mCanvas.drawLine(0, mBoundW / 2, 0, h - mBoundW / 2, mPaint);
            } else if (mLine == Line.Right) {
                mCanvas.drawLine(w, mBoundW / 2, w, h - mBoundW / 2, mPaint);
            }
        }
    }

    int textWidth = DensityUtil.dip2px(48);
    int textHeight = textWidth/2;

    private void drawTopTextBg(Canvas canvas) {
        float firstY = getTopFirstY();
//        Logger.w("feww = " + firstY);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mBoundW);
        int paintW = mBoundW / 2;
        mPaint.setColor(getResources().getColor(android.R.color.holo_purple));
        mPath.reset();
        mPath.moveTo(w - textWidth, firstY + paintW);
        mPath.lineTo(w - textWidth, firstY + textHeight + paintW);
        mPath.lineTo(w, mDst[7] + textHeight + paintW);
        mPath.lineTo(w, mDst[7] + paintW);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    private void drawFocus(Canvas canvas) {
        if (isHasFocus) {
            int paintW = mBoundW;
            mPaint.setStrokeWidth(paintW);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.parseColor("#007EFD"));
            paintW = paintW / 2;
            mPath.reset();
            mPath.moveTo(mDst[0] + paintW, mDst[1] + paintW);
            mPath.lineTo(mDst[2] + paintW, h - mDst[3] - paintW);
            mPath.lineTo(w - mDst[4] - paintW, h - mDst[5] - paintW);
            mPath.lineTo(w - mDst[6] - paintW, mDst[7] + paintW);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }
    }

    private void drawBgColor(Canvas canvas) {
        int paintW = mBoundW;
        mPaint.setStrokeWidth(paintW);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#e6004a"));
        paintW = paintW / 2;
        mPath.reset();
        mPath.moveTo(mDst[0] + paintW, mDst[1] + paintW);
        mPath.lineTo(mDst[2] + paintW, h - mDst[3] - paintW);
        mPath.lineTo(w - mDst[4] - paintW, h - mDst[5] - paintW);
        mPath.lineTo(w - mDst[6] - paintW, mDst[7] + paintW);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    void handBound(final Bitmap bitmap) {
        if (mLine == Line.No) return;
        if (boundColor != 0) return;
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                int dominantColor = Palette.from(bitmap).generate().getDominantColor(Color.LTGRAY);
                dominantColor = Palette.from(bitmap).generate().getVibrantColor(Color.LTGRAY);
                emitter.onNext(ColorUtils.setAlphaComponent(dominantColor, 200));
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                boundColor = integer;
                postInvalidate();
            }
        });
    }

    public float[] getDst() {
        return mDst;
    }

    public enum Line {Left, Right, No}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Logger.v("onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        Logger.d("onLayout");
    }


    private float getTopFirstY() { // 根据右上角文本的宽度获取绘制起点的y坐标，正好和图片上边重合
        float result = mDst[7] - mDst[1];
        if (result > 0) {
            return DensityUtil.height() * 1.5f / 17 / DensityUtil.width() * (w - textWidth);
        } else if (result < 0) {
            return DensityUtil.height() * 0.015f - DensityUtil.height() * 1.5f / 17 / DensityUtil.width() * (w - textWidth);
        } else {
            return 0;
        }
    }

    protected void setLine() {
        int offset = (int) ((mDst[1] + mDst[3]) - (mDst[5] + mDst[7]));
        if (offset < 0) {
            mLine = Line.Left;
        } else if (offset > 0) {
            mLine = Line.Right;
        } else {
            mLine = Line.No;
        }
    }
}
