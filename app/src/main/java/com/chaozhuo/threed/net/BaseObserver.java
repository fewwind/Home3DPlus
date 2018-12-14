package com.chaozhuo.threed.net;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by fewwind on 18-2-26.
 */

public abstract class BaseObserver<T extends BaseResponse> implements Observer<T> {

    Disposable d;
    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        onStart(d);
    }

    @Override
    public void onNext(T baseResponse) {
        if (baseResponse == null ||baseResponse.getCode() != 0){
            //onFail(baseResponse==null?"":baseResponse.getMsg(),null);
        }else {
            onSuccess(baseResponse);
        }
    }

    @Override
    public void onError(Throwable e) {
        d.dispose();
        onFail("",e);
    }


    @Override
    public void onComplete() {
        d.dispose();
    }

    public abstract void onSuccess(T t);
    public abstract void onFail(String msg, Throwable e);
    public void onStart(Disposable d){}
}
