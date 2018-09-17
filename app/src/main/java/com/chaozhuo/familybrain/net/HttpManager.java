package com.chaozhuo.familybrain.net;

import com.chaozhuo.familybrain.BuildConfig;
import com.chaozhuo.updateconfig.CZUpdateCryptUtil;
import com.chaozhuo.updateconfig.SDKConfig;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by fewwind on 18-2-26.
 */

public class HttpManager {

    private static int TIME_OUT = 20;
    private Retrofit mRetrofit;
    private HttpService mHttpService;

    private HttpManager(){

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder build = chain.request()
                        .newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("X-ApiKey", SDKConfig.getsInstance().getAPI_KEY())
                        .addHeader("X-ApiEncoding", "cbc");
                Request request = build.build();

                RequestBody requestBody = request.body();
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                byte[] bytes = buffer.readByteArray();

                Map<String, String> params = new HashMap();
                SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                String timeStamp = formatter.format(new Date(System.currentTimeMillis()));
                params.put("timestamp", timeStamp);

                String pTimeStamp = "timestamp=" + URLEncoder.encode(timeStamp, "UTF-8");

                String pSign = "";
                try {
                    pSign= "sign=" + URLEncoder.encode(
                            CZUpdateCryptUtil.sign(params, SDKConfig.getsInstance().getSECRET_KEY(),bytes), "UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Request.Builder newBuilder = request.newBuilder();
                newBuilder.method(request.method(),RequestBody.create(MEDIA_TYPE,bytes));
                newBuilder.url(request.url()+ "?" + pSign + "&" + pTimeStamp);

                Request build1 = newBuilder.build();
                return chain.proceed(build1);
            }

        });
        if (BuildConfig.DEBUG){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient client = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(SDKConfig.getsInstance().getHOST())
                .client(client)
                .addConverterFactory(DecodeConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mHttpService = mRetrofit.create(HttpService.class);
    }
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static class SingleInstance{
        public static final HttpManager instance= new HttpManager();
    }

    public static HttpManager get(){
        return SingleInstance.instance;
    }

    public HttpService getService(){
        return mHttpService;
    }

    public static <T> T creatApi(Class<T> clazz){
        return get().mRetrofit.create(clazz);
    }
}
