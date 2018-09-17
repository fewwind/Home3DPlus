package com.chaozhuo.familybrain.view.view3d;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.chaozhuo.familybrain.R;
import com.chaozhuo.familybrain.bean.ThreeDBean;
import com.chaozhuo.familybrain.util.DensityUtil;
import com.chaozhuo.familybrain.util.ResourceUtils;

/**
 * Created by fewwind on 18-8-27.
 */

public class ThreeDColorLayout extends FrameLayout {
    ThreeDView mView;
    RotateTextView mTvTitle;
    RotateImageView mIvIcon;
    ThreeDBean mBean;

    public ThreeDColorLayout(Context context) {
        this(context, null);
    }

    public ThreeDColorLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeDColorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_3d_layout_color, this);
        mView = findViewById(R.id.threeD_view);
        mTvTitle = findViewById(R.id.threeD_tv);
        mIvIcon = findViewById(R.id.threeD_iv);
        mIvIcon.setVisibility(GONE);
    }

    public void setThreeDBean(ThreeDBean bean) {
        this.mBean = bean;
        mView.setImageResource(ResourceUtils.getDrawableIdByVar(bean.imgUrl));
        mView.setDst(bean.destPos);
        mTvTitle.setDegrees(getBottomDegree());
        mIvIcon.setDegrees(getBottomDegree());
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
