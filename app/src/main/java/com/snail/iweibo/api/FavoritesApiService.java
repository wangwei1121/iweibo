package com.snail.iweibo.api;

import com.snail.iweibo.mvp.model.Favorite;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * FavoritesApiService
 * Created by alexwan on 16/4/1.
 */
public interface FavoritesApiService {

    /**
     * 添加一条微博到收藏里
     * @param token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param id 要收藏的微博ID
     * @return Observable
     */
    @FormUrlEncoded
    @POST("2/favorites/create.json")
    Observable<Favorite> createFavorites(@Field("access_token") String token , @Field("id") long id);

    /**
     * 取消收藏微博
     * @param token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param id 要收藏的微博ID
     * @return Observable
     */
    @FormUrlEncoded
    @POST("2/favorites/destroy.json")
    Observable<Favorite> destroyFavorites(@Field("access_token") String token , @Field("id") long id);


}
