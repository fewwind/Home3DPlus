package com.chaozhuo.threed.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.chaozhuo.threed.bean.ThreeDBean;
import com.chaozhuo.threed.util.DensityUtil;
import com.orhanobut.logger.Logger;

/**
 * Created by fewwind on 18-9-10.
 */


public class TouchTextView extends TextView {
    private static final int DEFAULT_DEGREES = 0;
    public static final int DEGREES_ZERO = 0;
    public static final int DEGREES_NEGACTIVE = -1;//负数
    public static final int DEGREES_POSITIVE = 1;//正数
    private float mDegrees;
    Paint mPaint;

    public TouchTextView(Context context) {
        this(context, null);
    }

    public TouchTextView(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.textViewStyle);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mDegrees != 0) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + DensityUtil.getHeightOffset());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean b = super.onTouchEvent(event);
        Logger.i(b + "TV " + event);
        return b;
    }

    public void setThreeDBean(ThreeDBean bean) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDegrees == 0) {
            super.onDraw(canvas);
        } else {
            canvas.save();
            float textSize = getTextSize();
            textSize += DensityUtil.dip2px(8);
//        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
            Logger.d(mDegrees + " ** height " + getMeasuredHeight());
            canvas.rotate(mDegrees, this.getMeasuredWidth() / 2f, this.getMeasuredHeight() / 2f);
//        if (mDegrees>0){
//            canvas.drawRect(16,getMeasuredHeight() / 2f-16,getMeasuredWidth()+36,getMeasuredHeight() / 2f-16F+36,mPaint);
//        } else {
//            canvas.drawRect(0,getMeasuredHeight() / 2f-16,getMeasuredWidth()+36,getMeasuredHeight() / 2f-16F+36,mPaint);
//        }
//            canvas.drawRect(-textSize / 2, getMeasuredHeight() / 2f - textSize / 2, getMeasuredWidth() + textSize / 2, getMeasuredHeight() / 2f + textSize / 2, mPaint);
            super.onDraw(canvas);
            canvas.restore();
        }

    }

    public void setDegrees(float degrees) {
        mDegrees = degrees;
    }
}