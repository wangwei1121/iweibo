package com.snail.iweibo.api;
import com.snail.iweibo.mvp.model.Statuses;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by alexwan on 16/1/30.
 */
public interface IStatuse {
    /**
     *
     * @param token 必选：采用OAuth授权方式为必填参数OAuth授权后获得。
     * @param count 单页返回的记录条数，默认为50。
     * @param page 返回结果的页码，默认为1。
     * @param app 是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
     * @return
     */
    @GET("2/statuses/public_timeline.json")
    Observable<Statuses> getPublicTimeLine(@Query("access_token") String token, @Query("count") int count, @Query
        ("page") int page, @Query("base_app") int app);
}
