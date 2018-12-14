package com.chaozhuo.threed.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.chaozhuo.threed.R;
import com.chaozhuo.threed.transformer.RotateYTransformer;
import com.chaozhuo.threed.transformer.UltraDepthScaleTransformer;
import com.chaozhuo.threed.util.DensityUtil;
import com.chaozhuo.threed.util.InjectService;
import com.chaozhuo.threed.util.ResourceUtils;
import com.chaozhuo.threed.view.QuickPageAdapter;
import com.chaozhuo.threed.view.ViewPager;
import com.chaozhuo.threed.view.view3d.Game3DLayout;
import com.chaozhuo.threed.view.view3d.Home3DLayout;
import com.chaozhuo.threed.view.view3d.Movie3DLayout;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
//        mViewPager.setVisibility(View.GONE);
//        mGifView.setVisibility(View.GONE);
        test();
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
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

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

    private void test() {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(3);
//        new MyConsumer(queue).start();
//        new MyProduce(queue).start();
        int[] array = {2, 6, 78, 5, 9, 12, 14, 52};
        int len = array.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
            Logger.w(Arrays.toString(array));
        }
//        startActivity(new Intent(this, ScrollingActivity.class));
    }


    int max = 5;

    class MyConsumer extends Thread {
        LinkedBlockingQueue<Integer> queue;

        public MyConsumer(LinkedBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            super.run();
            Integer poll = -1;
            while (true) {
                try {
                    poll = queue.take();
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logger.e("消费之 = " + poll);
            }
        }
    }

    class MyProduce extends Thread {
        LinkedBlockingQueue<Integer> queue;
        int i = 0;

        public MyProduce(LinkedBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            super.run();
            while (i < 36) {
                try {
                    i++;
                    queue.put(i);
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Logger.v("生产者 制造 = " + i);
            }
        }
    }
}
