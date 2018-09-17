package com.chaozhuo.familybrain.base;

/**
 * Created by fewwind on 18-8-17.
 */

public class Observable {
    private Inter mInter;

    public Observable(Inter mInter) {
        this.mInter = mInter;
    }

    public static Observable creat(Inter inter) {
        return new Observable(inter);
    }

    public void subscrib(Action action){
        mInter.call(action);
    }
    public interface Inter {
        void call(Action action) ;
    }

    public interface Action {
        void next();
    }
}
