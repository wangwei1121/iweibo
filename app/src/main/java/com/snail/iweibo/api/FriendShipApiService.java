package com.snail.iweibo.api;

import com.snail.iweibo.mvp.model.FollowersList;
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
     *
     * @param token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @return Observable<Group>
     */
    @GET("2/friendships/groups.json")
    Observable<GroupList> getGroups(@Query("access_token") String token);

    /**
     * 获取当前用户的粉丝列表
     *
     * @param token       采用OAuth授权方式为必填参数 OAuth授权后获得。
     * @param uid         需要查询的用户UID
     * @param screen_name 需要查询的用户昵称
     * @param count       单页返回的记录条数，默认为50，最大不超过200。
     * @param cursor      返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0。
     * @param trim_status 返回值中user字段中的status字段开关，0：返回完整status字段、1：status字段仅返回status_id，默认为1。
     * @return Observable<FollowersList>
     */
    @GET("2/friendships/followers.json")
    Observable<FollowersList> getFollowers(@Query("access_token") String token,
                                           @Query("uid") int uid,
                                           @Query("screen_name") String screen_name,
                                           @Query("count") int count,
                                           @Query("cursor") int cursor,
                                           @Query("trim_status") int trim_status);


}
