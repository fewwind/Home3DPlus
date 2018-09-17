package com.chaozhuo.familybrain.view.view3d;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.chaozhuo.familybrain.R;
import com.chaozhuo.familybrain.base.I3DLayout;
import com.chaozhuo.familybrain.bean.ThreeDBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fewwind on 18-8-27.
 */

public class Home3DLayout extends FrameLayout implements I3DLayout {

    private ThreeDLayout view1;
    private ThreeDLayout view2;
    private ThreeDLayout view3;
    private ThreeDLayout view4;
    private ThreeDLayout view5;
    private ThreeDLayout view6;
    private ThreeDLayout view7;
    private ThreeDLayout view8;
    List<ThreeDBean> mListData = new ArrayList<>();
    List<ThreeDLayout> mListView = new ArrayList<>();
    ReflectView mReflectMid;

    public Home3DLayout(@NonNull Context context) {
        this(context, null);
    }

    public Home3DLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Home3DLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_home_3d, this);
        view1 = findViewById(R.id.three_layout1);
        view2 = findViewById(R.id.three_layout2);
        view3 = findViewById(R.id.three_layout3);
        view4 = findViewById(R.id.three_layout4);
        view5 = findViewById(R.id.three_layout5);
        view6 = findViewById(R.id.three_layout6);
        view7 = findViewById(R.id.three_layout7);
        view8 = findViewById(R.id.three_layout8);
        initData();

        float[] left = {2f / 10, 8.8f / 10, 1, 8.5f / 10, 1, 1};
        float[] right = {0, 8.5f / 10, 8f / 10, 8.8f / 10, 0, 1};

    }

    private void initData() {
        float[] des1 = {0, 0, 0, 0, 0, 0, 0, hPadding};
        float[] des2 = {0, 0, 0, 0, 0, hPadding, 0, 0};
        float[] des3 = {0, 0, 0, 0, 0, 0, 0, hPadding};
        float[] des4 = {0, 0, 0, 0, 0, hPadding, 0, 0};
        float[] des5 = {0, 0, 0, 0, 0, 0, 0, 0};

        float[] des6 = {0, hPadding, 0, hPadding, 0, 0, 0, 0};
        float[] des7 = {0, hPadding, 0, 0, 0, 0, 0, 0};
        float[] des8 = {0, 0, 0, hPadding, 0, 0, 0, 0};
        ThreeDBean bean1 = new ThreeDBean(des1, "game_1", 0, "", "");
        ThreeDBean bean2 = new ThreeDBean(des2, "game_2", 0, "", "");
        ThreeDBean bean3 = new ThreeDBean(des3, "game_3", 0, "", "");
        ThreeDBean bean4 = new ThreeDBean(des4, "game_4", 0, "", "");
        ThreeDBean bean5 = new ThreeDBean(des5, "game_5", 0, "", "");
        ThreeDBean bean6 = new ThreeDBean(des6, "game_6", 0, "", "");
        ThreeDBean bean7 = new ThreeDBean(des7, "game_7", 0, "", "");
        ThreeDBean bean8 = new ThreeDBean(des8, "game_8", 0, "", "");
        mListData.add(bean1);
        mListData.add(bean2);
        mListData.add(bean3);
        mListData.add(bean4);
        mListData.add(bean5);
        mListData.add(bean6);
        mListData.add(bean7);
        mListData.add(bean8);

        mListView.add(view1);
        mListView.add(view2);
        mListView.add(view3);
        mListView.add(view4);
        mListView.add(view5);
        mListView.add(view6);
        mListView.add(view7);
        mListView.add(view8);
        for (int i = 0; i < mListView.size(); i++) {
            mListView.get(i).setThreeDBean(mListData.get(i));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Logger.e("Grpup = onMeasure");

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

}
