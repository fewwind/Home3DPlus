package com.chaozhuo.threed.net;

import com.chaozhuo.threed.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.ByteString;
import retrofit2.Converter;

/**
 * Created by User
 */

public class DecodeRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    public static String res = "";
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    DecodeRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter, value);
        jsonWriter.flush();
        String result = "";
        ByteString byteString = buffer.readByteString();
        byte[] bytes = result.getBytes();

        LogUtils.i("DecodeRequestBodyConverter", result + " *请求参数---" + byteString.utf8());
        return RequestBody.create(MEDIA_TYPE, bytes);
    }
}