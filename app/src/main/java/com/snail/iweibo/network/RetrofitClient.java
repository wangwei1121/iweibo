package com.snail.iweibo.network;
import com.snail.iweibo.api.IStatuse;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * RetrofitClient
 * Created by alexwan on 16/1/30.
 */
public class RetrofitClient {
    private static OkHttpClient client;
    private Retrofit retrofit;

    /**
     * getStatuseService
     * @return IStatuse
     */
    public static IStatuse getStatuseService() {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.weibo.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(new OkHttpClient.Builder().build()).build();
        return retrofit.create(IStatuse.class);
    }
}
