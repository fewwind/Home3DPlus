package com.chaozhuo.threed.view.view3d;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.chaozhuo.threed.R;
import com.chaozhuo.threed.bean.ThreeDBean;
import com.chaozhuo.threed.util.DensityUtil;
import com.chaozhuo.threed.util.ResourceUtils;

/**
 * Created by fewwind on 18-8-27.
 */

public class ThreeDLayout extends FrameLayout {
    ThreeDView mView;
    RotateTextView mTvRight;
    RotateTextView mTvBottom;
    ThreeDBean mBean;

    public ThreeDLayout(Context context) {
        this(context, null);
    }

    public ThreeDLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeDLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_3d_layout, this);
        mView = findViewById(R.id.threeD_view);
        mTvRight = findViewById(R.id.threeD_tv_right);
        mTvBottom = findViewById(R.id.threeD_tv_bottom);
    }

    public void setThreeDBean(ThreeDBean bean) {
        this.mBean = bean;
        mView.setImageResource(ResourceUtils.getDrawableIdByVar(bean.imgUrl));
        mView.setDst(bean.destPos);
        mTvBottom.setDegrees(getBottomDegree());
    }

    private float getBottomDegree() {
        float result = mBean.destPos[3] - mBean.destPos[5];
        if (result > 0) {
            return DensityUtil.getDegrees();
        } else if (result < 0) {
            return -DensityUtil.getDegrees();
        } else {
            return 0;
        }
    }

    private float getTopDegree() {
        float result = mBean.destPos[7] - mBean.destPos[1];
        if (result > 0) {
            return DensityUtil.getDegrees();
        } else if (result < 0) {
            return DensityUtil.getDegrees();
        } else {
            return 0;
        }
    }
}
