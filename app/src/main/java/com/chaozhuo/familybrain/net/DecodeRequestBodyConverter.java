package com.chaozhuo.familybrain.net;

import com.chaozhuo.familybrain.App;
import com.chaozhuo.familybrain.util.ChaoZhuoUtils;
import com.chaozhuo.familybrain.util.LogUtils;
import com.chaozhuo.updateconfig.CZUpdateCryptUtil;
import com.chaozhuo.updateconfig.CZUpdateQuery;
import com.chaozhuo.updateconfig.SDKConfig;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import org.json.JSONException;
import org.json.JSONObject;

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
    private CZUpdateQuery mQuery;
    DecodeRequestBodyConverter(Gson gson, TypeAdapter<T> adapter){
        this.gson = gson;
        this.adapter = adapter;
    }
    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(),UTF_8);
        JsonWriter jsonWriter = gson.newJsonWriter(writer);
        adapter.write(jsonWriter,value);
        jsonWriter.flush();
        String result = "";
        ByteString byteString = buffer.readByteString();
        try {
            JSONObject object = new JSONObject(byteString.utf8());
            if (mQuery == null){
                mQuery = new CZUpdateQuery();
            }
            object.put("platform", ChaoZhuoUtils.getPlantform());
            result = mQuery.toJsonString(App.app,object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        byte[] bytes = result.getBytes();

        try {
            bytes = CZUpdateCryptUtil.encrypt(bytes, SDKConfig.getsInstance().getSECRET_KEY(),CZUpdateCryptUtil.AES_CBC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.i("DecodeRequestBodyConverter",  result+" *请求参数---" + byteString.utf8());
        return RequestBody.create(MEDIA_TYPE,bytes);
    }
}