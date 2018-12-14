package com.chaozhuo.threed.net;

/**
 * Created by fewwind on 18-2-26.
 */

public class BaseResponse<T> {

    private int errno;
    private String message;
    private T data;

    public String getMsg() {
        return message;
    }

    public T getData() {
        return data;
    }


    public int getCode() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "errno=" + errno +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
