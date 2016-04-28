package com.snail.iweibo.api;

import com.snail.iweibo.mvp.model.AtUserInfo;
import com.snail.iweibo.mvp.model.GroupList;
import com.snail.iweibo.mvp.model.UserBeanList;

import java.util.List;

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

    /**
     * -@用户时的联想建议
     * @param token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param keyword 搜索的关键字，必须做URLencoding。
     * @param count 返回的记录条数，默认为10，粉丝最多1000，关注最多2000。
     * @param type 联想类型，0：关注、1：粉丝。
     * @param range 联想范围，0：只联想关注人、1：只联想关注人的备注、2：全部，默认为2。
     * @return List<AtUserInfo>
     */
    @GET("2/search/suggestions/at_users.json")
    Observable<List<AtUserInfo>> getAtUsers(@Query("access_token") String token , @Query("q") String keyword ,
                                            @Query("count") int count , @Query("type") int type ,
                                            @Query("range") int range);

    /**
     * 获取用户的关注列表
     * @param token token 采用OAuth授权方式为必填参数，OAuth授权后获得。
     * @param uid uid 需要查询的用户UID
     * @param screenName screenName 需要查询的用户昵称
     * @param count count 单页返回的记录条数，默认为50，最大不超过200。
     * @param cursor cursor 返回结果的游标，下一页用返回值里的next_cursor，上一页用previous_cursor，默认为0。
     * @param trimStatus trimStatus 返回值中user字段中的status字段开关，0：返回完整status字段、1：status字段仅返回status_id，默认为1。
     * @return UserBeanList
     */
    @GET("2/friendships/friends.json")
    Observable<UserBeanList> getFriends(@Query("access_token") String token, @Query("uid") String uid,
                                        @Query("screen_name") String screenName, @Query("count") int count,
                                        @Query("cursor") int cursor, @Query("trim_status") int trimStatus);
}
