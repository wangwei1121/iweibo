package com.snail.iweibo.network;
import android.util.Log;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitClient
 * Created by alexwan on 16/1/30.
 */
public class RetrofitClient {

    public static Retrofit instance(String baseUrl) {
        return new Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(new OkHttpClient() {
                @Override
                public List<Interceptor> interceptors() {
                    List<Interceptor> list = super.interceptors();
                    if (list != null && !list.isEmpty()) {
                        for (Interceptor in :
                            list) {
                            Log.i("RetrofitClient", in.toString());
                        }
                    }
                    return list;
                }
            }).build();
    }
}
