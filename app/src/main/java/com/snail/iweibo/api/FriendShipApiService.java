package com.snail.iweibo.api;

import com.snail.iweibo.mvp.model.GroupList;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 关系
 * Created by alexwan on 16/4/1.
 */
public interface FriendShipApiService {

    /**
     * 获取当前登陆用户好友分组列表
     * @param token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @return Observable<Group>
     */
    @GET("2/friendships/groups.json")
    Observable<GroupList> getGroups(@Query("access_token") String token);
}
