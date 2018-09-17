package com.chaozhuo.familybrain.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.chaozhuo.familybrain.R;
import com.chaozhuo.familybrain.transformer.RotateYTransformer;
import com.chaozhuo.familybrain.transformer.UltraDepthScaleTransformer;
import com.chaozhuo.familybrain.util.DensityUtil;
import com.chaozhuo.familybrain.util.InjectService;
import com.chaozhuo.familybrain.util.ResourceUtils;
import com.chaozhuo.familybrain.view.QuickPageAdapter;
import com.chaozhuo.familybrain.view.ViewPager;
import com.chaozhuo.familybrain.view.view3d.Game3DLayout;
import com.chaozhuo.familybrain.view.view3d.Home3DLayout;
import com.chaozhuo.familybrain.view.view3d.Movie3DLayout;

import java.util.ArrayList;
import java.util.List;

public class ThreeDActivity extends AppCompatActivity {

    ViewPager mViewPager;
    List<View> mDatas = new ArrayList<>();
    ImageView mGifView;
    AnimationDrawable anim;
    boolean isActive;
    boolean isGrag;
    int px;
    int py;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3d);
        mViewPager = findViewById(R.id.id_pager);
        mGifView = findViewById(R.id.gif_view);
        initData();
        handGifAnim();
    }

    private int mInitPx = -1;
    private int mInitPy = -1;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private void handGifAnim() {
        anim = new AnimationDrawable();
        String s = "";
        for (int i = 0; i < 271; i++) {
            if (i < 10) {
                s = "d1_0000";
            } else if (i < 100) {
                s = "d1_000";
            } else {
                s = "d1_00";
            }
            anim.addFrame(getResources().getDrawable(ResourceUtils.getDrawableIdByVar(s + i)), (int) (1000f / 30));
        }
        anim.setOneShot(false);
        mGifView.setImageDrawable(anim);
        anim.start();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                anim.stop();
            }
        }, 6000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                anim.start();
            }
        }, 8000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void initData() {
        mDatas.add(new Home3DLayout(this));
        mDatas.add(new Movie3DLayout(this));
        mDatas.add(new Game3DLayout(this));
        mDatas.add(new Home3DLayout(this));
        mDatas.add(new Movie3DLayout(this));
        mDatas.add(new Game3DLayout(this));
        mViewPager.setAdapter(new QuickPageAdapter<View>(mDatas));
//        mViewPager.setPageTransformer(true, new RotateDownPageTransformer());
//        mViewPager.setPageTransformer(true, new CubePageTransformer());
        mViewPager.setPageTransformer(true, new RotateYTransformer());
        mViewPager.setPageTransformer(true, new UltraDepthScaleTransformer());
//        mViewPager.setPageTransformer(true, new CubeInRotationTransformation());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE && isGrag) {
                    InjectService.get().invokeInjectInputEvent(ThreeDActivity.this, creatEvent(MotionEvent.ACTION_DOWN));
                }
            }
        });
    }

    private void change(int x) {
        x = 9;
    }

    public MotionEvent creatEvent(int action) {
        if (action == MotionEvent.ACTION_DOWN) {
            mInitPx = px;
            mInitPy = py;
        } else if (action == MotionEvent.ACTION_UP) {
            if (px > mInitPx) {
                px = (int) Math.max(px * 1.5f, DensityUtil.width());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                    }
                });

            } else {
                px = (int) (px / 1.5f);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
                    }
                });

            }
        }
        return MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis(), action, (float) px, (float) py, 0);
    }
}
