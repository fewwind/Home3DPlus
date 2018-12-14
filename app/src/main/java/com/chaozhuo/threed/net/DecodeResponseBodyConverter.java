package com.chaozhuo.threed.net;

import com.chaozhuo.threed.util.LogUtils;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by User on 2016/11/21.
 */

public class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        byte[] bytes = value.bytes();

        String result = null;
        try {
            result = new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.i("DecodeResponseBodyConverter", "结果---" + result);
        //解密字符串
        return result == null ? null : adapter.fromJson(result);
    }
}