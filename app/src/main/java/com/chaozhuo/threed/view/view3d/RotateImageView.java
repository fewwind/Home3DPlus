package com.chaozhuo.threed.view.view3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.chaozhuo.threed.bean.ThreeDBean;

/**
 * Created by fewwind on 18-9-10.
 */


public class RotateImageView extends ImageView {
    private static final int DEFAULT_DEGREES = 0;
    private float mDegrees = 0;
    Paint mPaint;

    public RotateImageView(Context context) {
        this(context, null);
    }

    public RotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.textViewStyle);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);
    }

    public void setThreeDBean(ThreeDBean bean) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
                | Paint.FILTER_BITMAP_FLAG)); //设置图形、图片的抗锯齿。可用于线条等。按位或.
        if (mDegrees == 0) {
            super.onDraw(canvas);
        } else {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
            if (bitmapDrawable == null) return;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            Matrix matrix = new Matrix();
            matrix.setRotate(mDegrees);
            canvas.drawBitmap(bitmap, matrix, mPaint);
        }

    }

    public void setDegrees(float degrees) {
        mDegrees = degrees;
    }
}