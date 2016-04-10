package com.snail.iweibo.api;
import com.snail.iweibo.mvp.model.UserBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 账户
 * Created by alexwan on 16/4/1.
 */
public interface AccountApiService {

    /**
     * getUserInfo
     * @param token      采用OAuth授权方式为必填参数，OAuth授权后获得。必选
     * @param uid        需要查询的用户ID。
     * @param screenName 需要查询的用户昵称。
     * @return UserBean  参数uid与screen_name二者必选其一，且只能选其一；
     */
    @GET("2/users/show.json")
    Observable<UserBean> getUserInfo(@Query("access_token") String token,
                                     @Query("uid") String uid,
                                     @Query("screen_name") String screenName);
}
