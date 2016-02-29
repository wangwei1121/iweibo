package com.snail.iweibo.network;
import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * RetrofitClient
 * Created by alexwan on 16/1/30.
 */
public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit instance(String baseUrl) {
        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(new OkHttpClient.Builder().build()).build();
    }
}
